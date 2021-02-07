package quick.pager.pcloud.helper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import quick.pager.pcloud.mapper.MenuMapper;
import quick.pager.pcloud.mapper.ResourceMapper;
import quick.pager.pcloud.mapper.RoleMapper;
import quick.pager.pcloud.mapper.RoleMenuMapper;
import quick.pager.pcloud.mapper.RoleResourceMapper;
import quick.pager.pcloud.mapper.SysRoleMapper;
import quick.pager.pcloud.model.MenuDO;
import quick.pager.pcloud.model.ResourceDO;
import quick.pager.pcloud.model.RoleDO;
import quick.pager.pcloud.model.RoleMenuDO;
import quick.pager.pcloud.model.RoleResourceDO;
import quick.pager.pcloud.model.SysRoleDO;

/**
 * 菜单工具类
 *
 * @author siguiyang
 */
@Component
public class MenuHelper {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private ResourceMapper resourceMapper;
    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * 管理员角色
     */
    private static final List<String> ROLE_ADMIN_CODE = Lists.newArrayList("ROLE_SUPER_ADMIN", "ROLE_ADMIN");

    /**
     * 根据登录用户主键查询用户角色
     *
     * @param sysUserId 用户主键
     * @return 角色主键集
     */
    private List<Long> querySysRole(final Long sysUserId) {
        List<SysRoleDO> sysRoles = sysRoleMapper.selectList(new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getSysUserId, sysUserId));
        return sysRoles.stream()
                .map(SysRoleDO::getRoleId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据当前用户主键查询权限菜单
     *
     * @param sysUserId 用户主键
     * @return 菜单集
     */
    public List<MenuDO> selectMenuBySysUserId(final Long sysUserId) {

        List<Long> roleIds = this.querySysRole(sysUserId);

        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        return selectMenuByRoleIds(roleIds);
    }


    /**
     * 根据当前用户查询权限菜单
     *
     * @param sysUserId 用户主键
     * @return 菜单集
     */
    public List<Long> queryPermissionBySysUserId(final Long sysUserId) {
        List<Long> roleIds = this.querySysRole(sysUserId);

        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        return queryPermissionByRoleIds(roleIds);
    }

    /**
     * 根据当前用户查询权限菜单
     *
     * @param sysUserId 用户主键
     * @return 菜单集
     */
    public List<String> queryRoleBySysUserId(final Long sysUserId) {
        List<Long> roleIds = this.querySysRole(sysUserId);

        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        List<RoleDO> roleDOS = roleMapper.selectBatchIds(roleIds);

        return roleDOS.stream().map(RoleDO::getRoleCode).collect(Collectors.toList());
    }

    /**
     * 通过角色查询当前角色所拥有的权限
     *
     * @param roleIds 角色主键集
     * @return 菜单集
     */
    private List<Long> queryPermissionByRoleIds(final List<Long> roleIds) {
        // 角色所属权限编码
        List<RoleMenuDO> roleMenus = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenuDO>()
                .in(RoleMenuDO::getRoleId, roleIds));

        return Optional.ofNullable(roleMenus).orElse(Lists.newArrayList()).stream()
                .map(RoleMenuDO::getMenuId)
                .distinct()
                .collect(Collectors.toList());
    }


    /**
     * 通过角色查询当前角色所拥有的权限
     *
     * @param roleId 角色主键
     * @return 菜单集
     */
    public List<MenuDO> selectMenuByRoleId(final Long roleId) {
        return selectMenuByRoleIds(Collections.singletonList(roleId));
    }

    /**
     * 通过角色查询当前角色所拥有的权限
     *
     * @param roleIds 角色主键集
     * @return 菜单集
     */
    private List<MenuDO> selectMenuByRoleIds(final List<Long> roleIds) {

        List<RoleDO> roleDOS = roleMapper.selectBatchIds(roleIds);
        // 没有角色直接返回
        if (CollectionUtils.isEmpty(roleDOS)) {
            return Collections.emptyList();
        }
        // 是否存在超级管理员角色
        boolean admin = roleDOS.stream().anyMatch(item -> ROLE_ADMIN_CODE.contains(item.getRoleCode()));
        // 返回所有资源数据
        if (admin) {
            return menuMapper.selectList(new LambdaQueryWrapper<MenuDO>().orderByAsc(MenuDO::getSequence));
        }

        // 角色所属权限编码
        List<RoleMenuDO> roleMenus = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenuDO>()
                .in(RoleMenuDO::getRoleId, roleIds));

        if (CollectionUtils.isEmpty(roleMenus)) {
            return Collections.emptyList();
        }

        List<Long> menuIds = roleMenus.stream()
                .map(RoleMenuDO::getMenuId)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        return menuMapper.selectBatchIds(menuIds);
    }

    /**
     * 通过角色查询当前角色所拥有的权限
     *
     * @param roleId 角色主键
     * @return 菜单集
     */
    public List<ResourceDO> selectResourceByRoleId(final Long roleId) {
        return selectResourceByRoleIds(Collections.singletonList(roleId));
    }


    /**
     * 通过角色查询当前角色所拥有的权限
     *
     * @param roleIds 角色主键集
     * @return 菜单集
     */
    private List<ResourceDO> selectResourceByRoleIds(final List<Long> roleIds) {

        List<RoleDO> roleDOS = this.roleMapper.selectBatchIds(roleIds);
        // 没有角色直接返回
        if (CollectionUtils.isEmpty(roleDOS)) {
            return Collections.emptyList();
        }
        // 是否存在超级管理员角色
        boolean admin = roleDOS.stream().anyMatch(item -> ROLE_ADMIN_CODE.contains(item.getRoleCode()));
        // 返回所有资源数据
        if (admin) {
            return this.resourceMapper.selectList(new LambdaQueryWrapper<ResourceDO>().orderByAsc(ResourceDO::getId));
        }

        // 角色所属权限编码
        List<RoleResourceDO> roleResourceDOS = this.roleResourceMapper.selectList(new LambdaQueryWrapper<RoleResourceDO>()
                .in(RoleResourceDO::getRoleId, roleIds));

        if (CollectionUtils.isEmpty(roleResourceDOS)) {
            return Collections.emptyList();
        }

        List<Long> resourceIds = roleResourceDOS.stream()
                .map(RoleResourceDO::getResourceId)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(resourceIds)) {
            return Collections.emptyList();
        }

        return this.resourceMapper.selectBatchIds(resourceIds);
    }
}
