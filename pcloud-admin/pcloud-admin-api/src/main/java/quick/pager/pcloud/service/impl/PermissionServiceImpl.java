package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.helper.MenuHelper;
import quick.pager.pcloud.mapper.MenuMapper;
import quick.pager.pcloud.mapper.ResourceMapper;
import quick.pager.pcloud.mapper.RoleMapper;
import quick.pager.pcloud.mapper.RoleMenuMapper;
import quick.pager.pcloud.mapper.RoleResourceMapper;
import quick.pager.pcloud.model.MenuDO;
import quick.pager.pcloud.model.ResourceDO;
import quick.pager.pcloud.model.RoleDO;
import quick.pager.pcloud.model.RoleMenuDO;
import quick.pager.pcloud.model.RoleResourceDO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.AuthorizationRequest;
import quick.pager.pcloud.service.PermissionService;
import quick.pager.pcloud.utils.Assert;

/**
 * 权限
 *
 * @author siguiyang
 * @version 3.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private ResourceMapper resourceMapper;
    @Resource
    private MenuHelper menuHelper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult grant(final AuthorizationRequest request) {
        // 角色
        final Long roleId = request.getRoleId();
        // 路由
        final List<Long> routers = request.getRouters();
        // 访问资源
        final List<Long> resources = request.getResources();

        RoleDO roleDO = this.roleMapper.selectById(roleId);
        Assert.isTrue(Objects.nonNull(roleDO), () -> "角色不存在");

        // 处理路由权限
        this.doMenu(routers, roleId);
        // 处理访问资源权限
        this.doResource(resources, roleId);

        return ResponseResult.toSuccess();
    }

    // region 私有方法

    /**
     * 处理路由权限
     *
     * @param routers 路由权限
     * @param roleId  角色主键
     */
    private void doMenu(List<Long> routers, final Long roleId) {
        // 此角色历史权限
        List<MenuDO> menus = menuHelper.selectMenuByRoleId(roleId);
        // 1. 当前角色历史权限未分配，分配新权限
        if (CollectionUtils.isEmpty(menus) && CollectionUtils.isNotEmpty(routers)) {
            this.insertRoleMenu(routers, roleId);
            return;
        }
        // 2. 此角色历史权限存在，并且授予角色权限为空，则清空当前角色权限
        if (CollectionUtils.isNotEmpty(menus) && CollectionUtils.isEmpty(routers)) {
            Assert.isTrue(this.roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenuDO>()
                            .eq(RoleMenuDO::getRoleId, roleId)) > 0,
                    () -> "授权失败");
            return;
        }

        // 3. 一下是第三中清空
        // 剩余下值是取消的权限
        menus.stream().filter(menu -> !routers.contains(menu.getId())).forEach(menu -> {
            LambdaQueryWrapper<RoleMenuDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RoleMenuDO::getRoleId, roleId);
            wrapper.eq(RoleMenuDO::getMenuId, menu.getId());
            roleMenuMapper.delete(wrapper);
        });

        // 得到当前资源主键
        List<Long> menuIds = menus.stream()
                .map(MenuDO::getId)
                .collect(Collectors.toList());

        // 剩下的值是新增的权限菜单主键
        List<Long> permIds = routers.stream()
                .filter(perm -> !menuIds.contains(perm))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(permIds)) {
            this.insertRoleMenu(permIds, roleId);
        }
    }

    /**
     * 角色授权
     *
     * @param menuIds 菜单主键集
     * @param roleId  角色主键
     */
    private void insertRoleMenu(final List<Long> menuIds, final Long roleId) {
        List<MenuDO> menuDOS = this.menuMapper.selectBatchIds(menuIds);
        menuDOS.forEach(menu -> {
            RoleMenuDO roleMenu = new RoleMenuDO();
            roleMenu.setMenuId(menu.getId());
            roleMenu.setRoleId(roleId);
            roleMenuMapper.insert(roleMenu);
        });
    }

    /**
     * 处理访问资源权限
     *
     * @param resources 路由权限
     * @param roleId    角色主键
     */
    private void doResource(List<Long> resources, final Long roleId) {
        // 此角色历史权限
        List<ResourceDO> resourceDOS = menuHelper.selectResourceByRoleId(roleId);
        // 1. 当前角色历史权限未分配，分配新权限
        if (CollectionUtils.isEmpty(resourceDOS) && CollectionUtils.isNotEmpty(resources)) {
            this.insertRoleResource(resources, roleId);
            return;
        }
        // 2. 此角色历史权限存在，并且授予角色权限为空，则清空当前角色权限
        if (CollectionUtils.isNotEmpty(resourceDOS) && CollectionUtils.isEmpty(resources)) {
            Assert.isTrue(this.roleResourceMapper.delete(new LambdaQueryWrapper<RoleResourceDO>()
                            .eq(RoleResourceDO::getRoleId, roleId)) > 0,
                    () -> "授权失败");
            return;
        }

        // 3. 一下是第三中清空
        // 剩余下值是取消的权限
        resourceDOS.stream()
                .filter(resourceDO -> !resources.contains(resourceDO.getId()))
                .forEach(resourceDO ->
                        this.roleResourceMapper.delete(new LambdaQueryWrapper<RoleResourceDO>()
                                .eq(RoleResourceDO::getRoleId, roleId)
                                .eq(RoleResourceDO::getResourceId, resourceDO.getId())));

        // 得到当前资源主键
        List<Long> resourceIds = resourceDOS.stream()
                .map(ResourceDO::getId)
                .collect(Collectors.toList());

        // 剩下的值是新增的权限菜单主键
        List<Long> permIds = resources.stream()
                .filter(perm -> !resourceIds.contains(perm))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(permIds)) {
            this.insertRoleResource(permIds, roleId);
        }
    }

    /**
     * 角色资源授权
     *
     * @param resourceIds 访问资源主键集
     * @param roleId      角色主键
     */
    private void insertRoleResource(final List<Long> resourceIds, final Long roleId) {
        List<ResourceDO> resourceDOS = this.resourceMapper.selectBatchIds(resourceIds);
        resourceDOS.forEach(resourceDO -> {
            RoleResourceDO roleResourceDO = new RoleResourceDO();
            roleResourceDO.setResourceId(resourceDO.getId());
            roleResourceDO.setRoleId(roleId);
            this.roleResourceMapper.insert(roleResourceDO);
        });
    }

    // endregion
}
