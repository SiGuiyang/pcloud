package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.constants.SConsts;
import quick.pager.pcloud.dto.OpenPermissionDTO;
import quick.pager.pcloud.dto.OpenResourceDTO;
import quick.pager.pcloud.mapper.OpenAccountMapper;
import quick.pager.pcloud.mapper.OpenAccountResourceMapper;
import quick.pager.pcloud.mapper.OpenResourceMapper;
import quick.pager.pcloud.model.OpenAccountDO;
import quick.pager.pcloud.model.OpenAccountResourceDO;
import quick.pager.pcloud.model.OpenResourceDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.OpenAccountResourceRequest;
import quick.pager.pcloud.request.OpenPermissionPageRequest;
import quick.pager.pcloud.request.OpenResourceOtherRequest;
import quick.pager.pcloud.request.OpenResourcePageRequest;
import quick.pager.pcloud.request.OpenResourceSaveRequest;
import quick.pager.pcloud.service.OpenResourceService;
import quick.pager.pcloud.utils.Assert;

/**
 * 访问资源
 *
 * @author siguiyang
 */
@Service
@Slf4j
public class OpenResourceServiceImpl implements OpenResourceService {

    @Resource
    private OpenResourceMapper openResourceMapper;
    @Resource
    private OpenAccountMapper openAccountMapper;
    @Resource
    private OpenAccountResourceMapper openAccountResourceMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return ResourceDO
     */
    private OpenResourceDO convert(final OpenResourceSaveRequest request) {

        return OpenResourceDO.builder()
                .id(request.getId())
                .resourceUrl(request.getResourceUrl())
                .name(request.getName())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    /**
     * DO -> DTO
     *
     * @param resourceDO ResourceDO
     * @return ResourceDTO
     */
    private OpenResourceDTO convert(final OpenResourceDO resourceDO) {

        return OpenResourceDTO.builder()
                .id(resourceDO.getId())
                .hasChildren(Boolean.FALSE)
                .resourceUrl(resourceDO.getResourceUrl())
                .name(resourceDO.getName())
                .gmtModifiedDate(resourceDO.getGmtModifiedDate())
                .gmtModifiedName(resourceDO.getGmtModifiedName())
                .build();
    }

    /**
     * DO -> DTO
     *
     * @param resourceDO 开放访问资源
     * @return OpenPermissionDTO
     */
    private OpenPermissionDTO convert(final OpenResourceDO resourceDO, final List<Long> resourceIds) {

        return OpenPermissionDTO.builder()
                .id(resourceDO.getId())
                .name(resourceDO.getName())
                .resourceUrl(resourceDO.getResourceUrl())
                .permission(resourceIds.contains(resourceDO.getId()))
                .build();
    }

    @PostConstruct
    public void init() {
        this.refresh(null);
    }


    // endregion

    @Override
    public ResponseResult<List<OpenResourceDTO>> queryPage(final OpenResourcePageRequest request) {
        LambdaQueryWrapper<OpenResourceDO> wrapper = new LambdaQueryWrapper<OpenResourceDO>()
                .likeRight(StringUtils.isNotBlank(request.getName()), OpenResourceDO::getName, request.getName());

        Integer count = this.openResourceMapper.selectCount(wrapper);

        List<OpenResourceDTO> dtos = Collections.emptyList();
        if (count > 0) {
            List<OpenResourceDO> records = this.openResourceMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();

            dtos = records.stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    public ResponseResult<List<OpenResourceDTO>> queryList(final OpenResourceOtherRequest request) {

        LambdaQueryWrapper<OpenResourceDO> wrapper = getQueryWrapper(request);

        List<OpenResourceDO> resourceDOS = this.openResourceMapper.selectList(wrapper);

        return ResponseResult.toSuccess(resourceDOS.stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final OpenResourceSaveRequest request) {
        OpenResourceDO resourceDO = this.convert(request);
        Assert.isTrue(this.openResourceMapper.insert(resourceDO) > 0, () -> "创建资源失败");
        return ResponseResult.toSuccess(resourceDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final OpenResourceSaveRequest request) {
        OpenResourceDO resourceDO = this.convert(request);
        Assert.isTrue(this.openResourceMapper.updateById(resourceDO) > 0, () -> "更新资源失败");
        return ResponseResult.toSuccess(resourceDO.getId());
    }


    @Override
    public ResponseResult<List<OpenPermissionDTO>> permission(final OpenPermissionPageRequest request) {

        OpenAccountDO openAccountDO = this.openAccountMapper.selectById(request.getAccountId());

        Assert.isTrue(Objects.nonNull(openAccountDO), () -> "开放账户不存在");

        LambdaQueryWrapper<OpenResourceDO> wrapper = new LambdaQueryWrapper<OpenResourceDO>()
                .likeRight(StringUtils.isNotBlank(request.getName()), OpenResourceDO::getName, request.getName())
                .orderByDesc(OpenResourceDO::getId);

        Integer count = this.openResourceMapper.selectCount(wrapper);

        List<OpenPermissionDTO> dtos = Collections.emptyList();
        if (count > 0) {
            // 所有对外开放的接口
            List<OpenResourceDO> openResourceDOS = this.openResourceMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();

            // 应用拥有的权限
            List<OpenAccountResourceDO> openAccountResourceDOS = this.openAccountResourceMapper.selectList(new LambdaQueryWrapper<OpenAccountResourceDO>()
                    .eq(OpenAccountResourceDO::getAccountId, request.getAccountId()));
            // 拥有的权限
            List<Long> resourceIds = openAccountResourceDOS.stream()
                    .map(OpenAccountResourceDO::getResourceId)
                    .distinct()
                    .collect(Collectors.toList());

            dtos = openResourceDOS.stream()
                    .map(item -> this.convert(item, resourceIds))
                    .collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> grant(final OpenAccountResourceRequest request) {

        OpenAccountDO openAccountDO = this.openAccountMapper.selectById(request.getAccountId());

        Assert.isTrue(Objects.nonNull(openAccountDO), () -> "开放账户不存在");
        this.doResource(request.getResourceIds(), request.getAccountId());
        return ResponseResult.toSuccess();
    }

    @Override
    public ResponseResult<String> refresh(final Long id) {
        // 查询所有账户资源
        List<OpenAccountDO> openAccountDOS = this.openAccountMapper.selectList(new LambdaQueryWrapper<OpenAccountDO>()
                .eq(Objects.nonNull(id), OpenAccountDO::getId, id));
        // 查询所有资源
        List<OpenResourceDO> resourceDOS = this.openResourceMapper.selectList(Wrappers.emptyWrapper());
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        openAccountDOS.forEach(openAccountDO -> {
            // 查询当前账户资源
            List<OpenAccountResourceDO> openAccountResourceDOS = this.openAccountResourceMapper.selectList(new LambdaQueryWrapper<OpenAccountResourceDO>()
                    .eq(OpenAccountResourceDO::getAccountId, openAccountDO.getId()));
            List<Long> resourceIds = openAccountResourceDOS.stream()
                    .map(OpenAccountResourceDO::getResourceId)
                    .collect(Collectors.toList());

            // 得到当前角色拥有的资源
            opsForHash.put(SConsts.AUTHORITY_PREFIX, openAccountDO.getSecureId(), resourceDOS.stream()
                    .filter(resourceDO -> resourceIds.contains(resourceDO.getId()))
                    .map(OpenResourceDO::getResourceUrl)
                    .distinct()
                    .collect(Collectors.toList()));
        });
        return ResponseResult.toSuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.openResourceMapper.deleteById(id) > 0, () -> "删除资源失败");
        return ResponseResult.toSuccess(id);
    }

    // region 私有方法

    /**
     * 动态查询
     *
     * @param request 请求参数
     * @return 动态查询
     */
    private LambdaQueryWrapper<OpenResourceDO> getQueryWrapper(OpenResourceOtherRequest request) {
        return new LambdaQueryWrapper<OpenResourceDO>()
                .eq(StringUtils.isNotEmpty(request.getName()), OpenResourceDO::getName, request.getName())
                .in(CollectionUtils.isNotEmpty(request.getIds()), OpenResourceDO::getId, request.getIds());
    }

    /**
     * 处理访问资源权限
     *
     * @param resources 路由权限
     * @param accountId 账户主键
     */
    private void doResource(List<Long> resources, final Long accountId) {
        // 此角色历史权限
        List<OpenAccountResourceDO> openAccountResourceDOS = this.openAccountResourceMapper.selectList(new LambdaQueryWrapper<OpenAccountResourceDO>()
                .eq(OpenAccountResourceDO::getAccountId, accountId));

        List<OpenResourceDO> openResourceDOS = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(openAccountResourceDOS)) {
            openResourceDOS = this.openResourceMapper.selectBatchIds(openAccountResourceDOS.stream()
                    .map(OpenAccountResourceDO::getResourceId)
                    .collect(Collectors.toList()));
        }

        // 1. 当前应用历史权限未分配，分配新权限
        if (CollectionUtils.isEmpty(openResourceDOS) && CollectionUtils.isNotEmpty(resources)) {
            log.info("当前应用历史权限未分配，分配新权限，resources = {}, accountId", resources, accountId);
            this.insertAccountResource(resources, accountId);
            return;
        }
        // 2. 此应用历史权限存在，并且授予角色权限为空，则清空当前角色权限
        if (CollectionUtils.isNotEmpty(openResourceDOS) && CollectionUtils.isEmpty(resources)) {
            log.info("此应用历史权限存在，并且授予角色权限为空，则清空当前角色权限，resources = {}, accountId", resources, accountId);
            Assert.isTrue(this.openAccountResourceMapper.delete(new LambdaQueryWrapper<OpenAccountResourceDO>()
                            .eq(OpenAccountResourceDO::getAccountId, accountId)) > 0,
                    () -> "授权失败");
            return;
        }

        // 3. 一下是第三中清空
        // 剩余下值是取消的权限
        log.info("剩余下值是取消的权限，resources = {}, accountId", resources, accountId);
        openResourceDOS.stream()
                .filter(resourceDO -> !resources.contains(resourceDO.getId()))
                .forEach(resourceDO ->
                        this.openAccountResourceMapper.delete(new LambdaQueryWrapper<OpenAccountResourceDO>()
                                .eq(OpenAccountResourceDO::getAccountId, accountId)
                                .eq(OpenAccountResourceDO::getResourceId, resourceDO.getId())));

        // 得到当前资源主键
        List<Long> resourceIds = openResourceDOS.stream()
                .map(OpenResourceDO::getId)
                .collect(Collectors.toList());

        // 剩下的值是新增的权限菜单主键
        log.info("剩下的值是新增的权限菜单主键，resources = {}，resourceIds = {}, accountId", resources, resourceIds, accountId);
        List<Long> permIds = resources.stream()
                .filter(perm -> !resourceIds.contains(perm))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(permIds)) {
            this.insertAccountResource(permIds, accountId);
        }
    }

    /**
     * 角色资源授权
     *
     * @param resourceIds 访问资源主键集
     * @param accountId   账户主键
     */
    private void insertAccountResource(final List<Long> resourceIds, final Long accountId) {
        List<OpenResourceDO> resourceDOS = this.openResourceMapper.selectBatchIds(resourceIds);
        resourceDOS.forEach(resourceDO -> {
            OpenAccountResourceDO roleResourceDO = new OpenAccountResourceDO();
            roleResourceDO.setResourceId(resourceDO.getId());
            roleResourceDO.setAccountId(accountId);
            this.openAccountResourceMapper.insert(roleResourceDO);
        });
    }

    // endregion
}
