package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.constants.IConsts;
import quick.pager.pcloud.constants.LConsts;
import quick.pager.pcloud.constants.SConsts;
import quick.pager.pcloud.dto.ResourceDTO;
import quick.pager.pcloud.dto.ResourceTreeDTO;
import quick.pager.pcloud.enums.ResourceBizTypeEnums;
import quick.pager.pcloud.helper.MenuHelper;
import quick.pager.pcloud.mapper.MenuMapper;
import quick.pager.pcloud.mapper.ResourceMapper;
import quick.pager.pcloud.mapper.RoleMapper;
import quick.pager.pcloud.mapper.RoleMenuMapper;
import quick.pager.pcloud.mapper.RoleResourceMapper;
import quick.pager.pcloud.model.MenuDO;
import quick.pager.pcloud.model.ResourceDO;
import quick.pager.pcloud.model.RoleDO;
import quick.pager.pcloud.dto.MenuDTO;
import quick.pager.pcloud.dto.PermissionDTO;
import quick.pager.pcloud.dto.RoleDTO;
import quick.pager.pcloud.model.RoleMenuDO;
import quick.pager.pcloud.model.RoleResourceDO;
import quick.pager.pcloud.request.RoleOtherRequest;
import quick.pager.pcloud.request.RolePageRequest;
import quick.pager.pcloud.request.RoleSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.RoleService;
import quick.pager.pcloud.utils.Assert;

/**
 * 角色
 *
 * @author siguiyang
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuHelper menuHelper;
    @Resource
    private RoleResourceMapper roleResourceMapper;
    @Resource
    private ResourceMapper resourceMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return MenuDO
     */
    private RoleDO convert(final RoleSaveRequest request) {

        return RoleDO.builder()
                .id(request.getId())
                .roleCode(request.getRoleCode())
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
     * @param roleDO RoleDO
     * @return MenuDTO
     */
    private RoleDTO convert(final RoleDO roleDO) {
        return RoleDTO.builder()
                .id(roleDO.getId())
                .roleCode(roleDO.getRoleCode())
                .name(roleDO.getName())
                .gmtModifiedDate(roleDO.getGmtModifiedDate())
                .gmtModifiedName(roleDO.getGmtModifiedName())
                .build();
    }

    /**
     * DO -> DTO
     *
     * @param menuDO menuDO
     * @return MenuDTO
     */
    private MenuDTO convert(final MenuDO menuDO) {

        MenuDTO menuDTO = MenuDTO.builder()
                .id(menuDO.getId())
                .component(menuDO.getComponent())
                .hidden(menuDO.getHidden())
                .icon(menuDO.getIcon())
                .name(menuDO.getName())
                .parentId(menuDO.getParentId())
                .path(menuDO.getPath())
                .redirect(menuDO.getRedirect())
                .router(menuDO.getRouter())
                .sequence(menuDO.getSequence())
                .gmtModifiedDate(menuDO.getGmtModifiedDate())
                .gmtModifiedName(menuDO.getGmtModifiedName())
                .build();


        menuDTO.setMeta(new MenuDTO.Meta(menuDO.getName(), menuDO.getIcon(), false, null));
        if (IConsts.ZERO == LConsts.ZERO.compareTo(menuDO.getParentId())) {
            menuDTO.setComponent("Layout");
        }
        return menuDTO;
    }

    /**
     * DO -> DTO
     *
     * @param resourceDO ResourceDO
     * @return ResourceTreeDTO
     */
    private ResourceTreeDTO convert(final ResourceDO resourceDO) {

        return ResourceTreeDTO.builder()
                .id(resourceDO.getId())
                .parentId(resourceDO.getParentId())
                .hasChildren(Boolean.FALSE)
                .bizTypeName(ResourceBizTypeEnums.parse(resourceDO.getBizType()).getDesc())
                .name(resourceDO.getName())
                .label(resourceDO.getName())
                .build();
    }

    // endregion

    @PostConstruct
    public void init() {
        // 查询所有角色
        List<RoleDO> roleDOS = this.roleMapper.selectList(Wrappers.emptyWrapper());
        // 查询所有资源
        List<ResourceDO> resourceDOS = this.resourceMapper.selectList(Wrappers.emptyWrapper());
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        roleDOS.forEach(roleDO -> {
            List<RoleResourceDO> roleMenuDOS = this.roleResourceMapper.selectList(new LambdaQueryWrapper<RoleResourceDO>()
                    .eq(RoleResourceDO::getRoleId, roleDO.getId()));
            List<Long> resourceIds = roleMenuDOS.stream().map(RoleResourceDO::getResourceId).collect(Collectors.toList());
            // 得到当前角色拥有的资源
            List<String> resourceUrls = resourceDOS.stream()
                    .filter(resourceDO -> resourceIds.contains(resourceDO.getId()))
                    .map(ResourceDO::getResourceUrl)
                    .distinct()
                    .collect(Collectors.toList());
            opsForHash.put(SConsts.AUTHORITY_PREFIX, roleDO.getRoleCode(), resourceUrls);
        });
        // 超级管理员拥有所有访问权限
        opsForHash.put(SConsts.AUTHORITY_PREFIX, "ROLE_SUPER_ADMIN", resourceDOS.stream()
                .map(ResourceDO::getResourceUrl)
                .distinct()
                .collect(Collectors.toList()));

        // 超级管理员拥有所有访问权限
        opsForHash.put(SConsts.AUTHORITY_PREFIX, "ROLE_ADMIN", resourceDOS.stream()
                .map(ResourceDO::getResourceUrl)
                .distinct()
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<RoleDTO>> queryPage(final RolePageRequest request) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<RoleDO>()
                .eq(RoleDO::getDeleteStatus, Boolean.FALSE)
                .likeRight(StringUtils.isNotBlank(request.getName()), RoleDO::getName, request.getName());

        wrapper.orderByDesc(RoleDO::getGmtCreatedDate);

        Integer count = this.roleMapper.selectCount(wrapper);

        List<RoleDTO> dtos = Collections.emptyList();
        if (count > 0) {
            List<RoleDO> records = this.roleMapper.selectPage(new Page<>(request.getPage(), request.getPageSize()), wrapper).getRecords();

            dtos = records.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    public ResponseResult<List<RoleDTO>> queryList(final RoleOtherRequest request) {

        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<RoleDO>()
                .in(CollectionUtils.isNotEmpty(request.getIds()), RoleDO::getId, request.getIds())
                .eq(StringUtils.isNotEmpty(request.getRoleCode()), RoleDO::getRoleCode, request.getRoleCode())
                .likeRight(StringUtils.isNotBlank(request.getName()), RoleDO::getName, request.getName());

        List<RoleDO> roles = this.roleMapper.selectList(wrapper);

        return ResponseResult.toSuccess(roles.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<Long> create(final RoleSaveRequest request) {
        RoleDO role = this.convert(request);
        Assert.isTrue(this.roleMapper.insert(role) > 0, () -> "创建角色失败");
        return ResponseResult.toSuccess(role.getId());
    }

    @Override
    public ResponseResult<Long> modify(final RoleSaveRequest request) {
        RoleDO role = this.convert(request);
        Assert.isTrue(this.roleMapper.updateById(role) > 0, () -> "更新角色失败");
        return ResponseResult.toSuccess(role.getId());
    }

    @Override
    public ResponseResult<String> refresh() {
        this.init();
        return ResponseResult.toSuccess();
    }

    @Override
    public ResponseResult<Long> delete(final Long id) {
        RoleDO roleDO = this.roleMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(roleDO), () -> "角色不存在");
        Assert.isTrue(this.roleMapper.deleteById(id) > 0, () -> "删除角色失败");

        // 接口权限
        List<RoleResourceDO> roleResourceDOS = this.roleResourceMapper.selectList(new LambdaQueryWrapper<RoleResourceDO>().eq(RoleResourceDO::getRoleId, id));
        if (CollectionUtils.isNotEmpty(roleResourceDOS)) {
            Assert.isTrue(this.roleResourceMapper.delete(new LambdaQueryWrapper<RoleResourceDO>().eq(RoleResourceDO::getRoleId, id)) > 0,
                    () -> "删除角色资源失败");
        }
        // 角色关联的菜单
        List<RoleMenuDO> roleMenuDOS = this.roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenuDO>().eq(RoleMenuDO::getRoleId, id));
        if (CollectionUtils.isNotEmpty(roleMenuDOS)) {
            Assert.isTrue(this.roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenuDO>().eq(RoleMenuDO::getRoleId, id)) > 0,
                    () -> "删除角色菜单失败");
        }
        return ResponseResult.toSuccess(id);
    }

    @Override
    public ResponseResult<PermissionDTO> rolePermission(final Long roleId) {

        List<MenuDO> menus = menuMapper.selectList(Wrappers.emptyWrapper());
        // 1. 得到顶级菜单
        List<MenuDTO> parentMenuDTOS = Optional.ofNullable(menus).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO == LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.toList());
        // 按照parentId分组，用于树形结构组装数据
        Map<Long, List<MenuDTO>> childrenMap = Optional.ofNullable(menus).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO != LConsts.ZERO.compareTo(item.getParentId()) && item.getRouter())
                .map(this::convert)
                .collect(Collectors.groupingBy(MenuDTO::getParentId));
        // 组装数据
        toTree(parentMenuDTOS, childrenMap, Lists.newArrayList());
        // 所有用户访问菜单的路由
        List<MenuDO> allMenus = menuHelper.selectMenuByRoleId(roleId);

        // 2. 访问资源
        List<ResourceDO> resourceDOS = this.resourceMapper.selectList(Wrappers.emptyWrapper());
        // 得到顶级资源
        List<ResourceTreeDTO> parentResourceDTOS = Optional.ofNullable(resourceDOS).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO == LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.toList());
        // 按照parentId分组，用于树形结构组装数据
        Map<Long, List<ResourceTreeDTO>> childrenResourceMap = Optional.ofNullable(resourceDOS).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO != LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.groupingBy(ResourceTreeDTO::getParentId));

        // 组装数据
        toResourceTree(parentResourceDTOS, childrenResourceMap, Lists.newArrayList());
        // 所有用户访问资源的权限
        List<ResourceDO> resourceDOList = menuHelper.selectResourceByRoleId(roleId);

        return ResponseResult.toSuccess(PermissionDTO.builder()
                .hasRouters(Optional.ofNullable(allMenus).orElse(Collections.emptyList()).stream()
                        .map(MenuDO::getId).distinct().collect(Collectors.toList()))
                .routers(parentMenuDTOS)
                .hasResources(Optional.ofNullable(resourceDOList).orElse(Collections.emptyList()).stream()
                        .map(ResourceDO::getId).distinct().collect(Collectors.toList()))
                .resources(parentResourceDTOS)
                .build());
    }

    // region 私有方法

    /**
     * 树形结构转换
     *
     * @param parentResp  顶级菜单
     * @param childrenMap 孩子节点
     * @param parentId    记录所有具有孩子节点的Id
     */
    private void toTree(final List<MenuDTO> parentResp, final Map<Long, List<MenuDTO>> childrenMap, final List<Long> parentId) {
        parentResp.forEach(item -> {
            List<MenuDTO> list = childrenMap.get(item.getId());
            toTree(Optional.ofNullable(list).orElse(Collections.emptyList()), childrenMap, parentId);
            item.setChildren(list);
            if (!CollectionUtils.isEmpty(item.getChildren())) {
                parentId.add(item.getId());
            }
        });
    }

    /**
     * 树形结构转换
     *
     * @param parentResp  顶级菜单
     * @param childrenMap 孩子节点
     * @param parentId    记录所有具有孩子节点的Id
     */
    private void toResourceTree(final List<ResourceTreeDTO> parentResp, final Map<Long, List<ResourceTreeDTO>> childrenMap, final List<Long> parentId) {
        parentResp.forEach(item -> {
            List<ResourceTreeDTO> list = childrenMap.get(item.getId());
            toResourceTree(Optional.ofNullable(list).orElse(Collections.emptyList()), childrenMap, parentId);
            item.setChildren(list);
            if (!CollectionUtils.isEmpty(item.getChildren())) {
                parentId.add(item.getId());
            }
        });
    }

    // endregion
}
