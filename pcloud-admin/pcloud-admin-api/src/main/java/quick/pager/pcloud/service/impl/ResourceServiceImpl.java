package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.constants.IConsts;
import quick.pager.pcloud.constants.LConsts;
import quick.pager.pcloud.dto.ResourceDTO;
import quick.pager.pcloud.enums.ResourceBizTypeEnums;
import quick.pager.pcloud.mapper.ResourceMapper;
import quick.pager.pcloud.model.ResourceDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.ResourceOtherRequest;
import quick.pager.pcloud.request.ResourcePageRequest;
import quick.pager.pcloud.request.ResourceSaveRequest;
import quick.pager.pcloud.service.ResourceService;
import quick.pager.pcloud.utils.Assert;

/**
 * 访问资源
 *
 * @author siguiyang
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceMapper resourceMapper;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return ResourceDO
     */
    private ResourceDO convert(final ResourceSaveRequest request) {

        return ResourceDO.builder()
                .id(request.getId())
                .parentId(request.getParentId())
                .resourceUrl(request.getResourceUrl())
                .name(request.getName())
                .bizType(request.getBizType())
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
    private ResourceDTO convert(final ResourceDO resourceDO, boolean hasChildren) {

        ResourceDTO resourceDTO = ResourceDTO.builder()
                .id(resourceDO.getId())
                .parentId(resourceDO.getParentId())
                .hasChildren(Boolean.FALSE)
                .resourceUrl(resourceDO.getResourceUrl())
                .bizType(resourceDO.getBizType())
                .bizTypeName(ResourceBizTypeEnums.parse(resourceDO.getBizType()).getDesc())
                .name(resourceDO.getName())
                .label(resourceDO.getName())
                .gmtModifiedDate(resourceDO.getGmtModifiedDate())
                .gmtModifiedName(resourceDO.getGmtModifiedName())
                .build();

        if (hasChildren) {
            int count = this.resourceMapper.selectCount(new LambdaQueryWrapper<ResourceDO>().eq(ResourceDO::getParentId, resourceDO.getId()));
            resourceDTO.setHasChildren(count > 0);
        }
        return resourceDTO;
    }

    // endregion

    @Override
    public ResponseResult<List<ResourceDTO>> queryPage(final ResourcePageRequest request) {
        LambdaQueryWrapper<ResourceDO> wrapper = new LambdaQueryWrapper<ResourceDO>()
                .eq(ResourceDO::getParentId, request.getParentId())
                .likeRight(StringUtils.isNotBlank(request.getName()), ResourceDO::getName, request.getName());

        Integer count = this.resourceMapper.selectCount(wrapper);

        List<ResourceDTO> dtos = Collections.emptyList();
        if (count > 0) {
            List<ResourceDO> records = this.resourceMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();

            dtos = records.stream()
                    .map(item -> this.convert(item, true))
                    .collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    public ResponseResult<List<ResourceDTO>> queryList(final ResourceOtherRequest request) {

        LambdaQueryWrapper<ResourceDO> wrapper = getQueryWrapper(request);

        List<ResourceDO> resourceDOS = this.resourceMapper.selectList(wrapper);

        return ResponseResult.toSuccess(resourceDOS.stream()
                .map(item -> this.convert(item, true))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<ResourceDTO>> queryTree(final ResourceOtherRequest request) {
        LambdaQueryWrapper<ResourceDO> wrapper = getQueryWrapper(request);

        List<ResourceDO> resourceDOS = this.resourceMapper.selectList(wrapper);

        List<ResourceDTO> dtos = resourceDOS.stream()
                .filter(item -> IConsts.ZERO == LConsts.ZERO.compareTo(item.getParentId()))
                .map(item -> this.convert(item, false))
                .collect(Collectors.toList());
        Map<Long, List<ResourceDTO>> childrenMap = resourceDOS.stream()
                .filter(item -> IConsts.ZERO != LConsts.ZERO.compareTo(item.getParentId()))
                .map(item -> this.convert(item, false))
                .collect(Collectors.groupingBy(ResourceDTO::getParentId, Collectors.toList()));

        // 数据转换
        this.toTree(dtos, childrenMap);
        return ResponseResult.toSuccess(dtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final ResourceSaveRequest request) {
        ResourceDO resourceDO = this.convert(request);
        Assert.isTrue(this.resourceMapper.insert(resourceDO) > 0, () -> "创建资源失败");
        return ResponseResult.toSuccess(resourceDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final ResourceSaveRequest request) {
        ResourceDO resourceDO = this.convert(request);
        Assert.isTrue(this.resourceMapper.updateById(resourceDO) > 0, () -> "更新资源失败");
        return ResponseResult.toSuccess(resourceDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.resourceMapper.deleteById(id) > 0, () -> "删除资源失败");
        return ResponseResult.toSuccess(id);
    }

    // region 私有方法

    /**
     * 动态查询
     *
     * @param request 请求参数
     * @return 动态查询
     */
    private LambdaQueryWrapper<ResourceDO> getQueryWrapper(ResourceOtherRequest request) {
        return new LambdaQueryWrapper<ResourceDO>()
                .eq(Objects.nonNull(request.getParentId()), ResourceDO::getParentId, request.getParentId())
                .eq(Objects.nonNull(request.getBizType()), ResourceDO::getBizType, request.getBizType())
                .eq(StringUtils.isNotEmpty(request.getName()), ResourceDO::getName, request.getName())
                .in(CollectionUtils.isNotEmpty(request.getIds()), ResourceDO::getId, request.getIds());
    }

    /**
     * 树形结构转换
     *
     * @param parentResp  顶级菜单
     * @param childrenMap 孩子节点
     */
    private void toTree(final List<ResourceDTO> parentResp, final Map<Long, List<ResourceDTO>> childrenMap) {
        parentResp.forEach(item -> {
            List<ResourceDTO> list = childrenMap.get(item.getId());
            toTree(Optional.ofNullable(list).orElse(Collections.emptyList()), childrenMap);
            item.setChildren(list);
        });
    }

    // endregion
}
