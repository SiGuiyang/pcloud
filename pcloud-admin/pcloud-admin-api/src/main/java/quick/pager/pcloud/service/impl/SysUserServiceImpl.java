package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.constants.IConsts;
import quick.pager.pcloud.constants.LConsts;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.constants.SConsts;
import quick.pager.pcloud.helper.MenuHelper;
import quick.pager.pcloud.mapper.RoleMapper;
import quick.pager.pcloud.mapper.SysRoleMapper;
import quick.pager.pcloud.mapper.SysUserMapper;
import quick.pager.pcloud.model.MenuDO;
import quick.pager.pcloud.model.RoleDO;
import quick.pager.pcloud.model.SysRoleDO;
import quick.pager.pcloud.model.SysUserDO;
import quick.pager.pcloud.dto.MenuDTO;
import quick.pager.pcloud.dto.RoleDTO;
import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.dto.SysUserDownloadDTO;
import quick.pager.pcloud.model.request.SysUserOtherRequest;
import quick.pager.pcloud.model.request.SysUserPageRequest;
import quick.pager.pcloud.model.request.SysUserSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.SysUserService;
import quick.pager.pcloud.utils.Assert;

/**
 * 系统用户服务
 *
 * @author siguiyang
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuHelper menuHelper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return MenuDO
     */
    private SysUserDO convert(final SysUserSaveRequest request) {

        return SysUserDO.builder()
                .id(request.getId())
                .name(request.getName())
                .phone(request.getPhone())
                .state(request.getState())
                .avatar(request.getAvatar())
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
     * @param sysUserDO SysUserDO
     * @return SysUserDTO
     */
    private SysUserDTO convert(final SysUserDO sysUserDO) {
        SysUserDTO sysUserDTO = SysUserDTO.builder()
                .id(sysUserDO.getId())
                .name(sysUserDO.getName())
                .avatar(sysUserDO.getAvatar())
                .password(sysUserDO.getPassword())
                .phone(sysUserDO.getPhone())
                .state(sysUserDO.getState())
                .online(StringUtils.isEmpty((String) redisTemplate.opsForValue().get("pcloud:token:".concat(sysUserDO.getPhone()))))
                .gmtModifiedDate(sysUserDO.getGmtModifiedDate())
                .gmtModifiedName(sysUserDO.getGmtModifiedName())
                .build();
        List<SysRoleDO> sysRoleDOS = this.sysRoleMapper.selectList(new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getSysUserId, sysUserDO.getId()));

        if (CollectionUtils.isNotEmpty(sysRoleDOS)) {
            List<RoleDO> roleDOS = this.roleMapper.selectBatchIds(sysRoleDOS.stream().map(SysRoleDO::getRoleId).collect(Collectors.toList()));
            sysUserDTO.setRoles(roleDOS.stream().map(this::convert).collect(Collectors.toList()));
        }
        return sysUserDTO;
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

    // endregion

    @Override
    public ResponseResult<List<SysUserDTO>> queryPage(SysUserPageRequest request) {

        LambdaQueryWrapper<SysUserDO> wrapper = new LambdaQueryWrapper<SysUserDO>()
                .eq(StringUtils.isNotEmpty(request.getPhone()), SysUserDO::getPhone, request.getPhone())
                .like(StringUtils.isNotEmpty(request.getKeyword()), SysUserDO::getName, request.getKeyword());
        wrapper.orderByDesc(SysUserDO::getGmtCreatedDate);

        Integer total = this.sysUserMapper.selectCount(wrapper);
        List<SysUserDTO> responseList = Collections.emptyList();
        if (total > 0) {
            List<SysUserDO> list = this.sysUserMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();
            responseList = Optional.ofNullable(list).orElse(Collections.emptyList()).stream()
                    .map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(responseList, total);
    }

    @Override
    public ResponseResult<List<SysUserDTO>> queryList(SysUserOtherRequest request) {

        List<SysUserDO> sysUsers = this.sysUserMapper.selectList(new LambdaQueryWrapper<SysUserDO>()
                .eq(StringUtils.isNotEmpty(request.getPhone()), SysUserDO::getPhone, request.getPhone())
                .in(CollectionUtils.isNotEmpty(request.getIds()), SysUserDO::getId, request.getIds())
        );

        return ResponseResult.toSuccess(sysUsers.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<String> offline(final Long id) {
        SysUserDO sysUserDO = this.sysUserMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(sysUserDO), () -> "用户不存在");
        // 删除token
        redisTemplate.delete("pcloud:token:".concat(sysUserDO.getPhone()));

        return ResponseResult.toSuccess();
    }

    @Override
    public List<SysUserDownloadDTO> queryDownload(List<Long> ids) {

        List<SysUserDO> sysUsers = this.sysUserMapper.selectBatchIds(ids);

        return sysUsers.stream().map(item -> {
            SysUserDownloadDTO downloadDTO = new SysUserDownloadDTO();
            downloadDTO.setName(item.getName());
            downloadDTO.setPhone(item.getPhone());
            downloadDTO.setGmtCreatedDate(item.getGmtCreatedDate());

            List<SysRoleDO> sysRoles = this.sysRoleMapper.selectList(new LambdaQueryWrapper<SysRoleDO>()
                    .eq(SysRoleDO::getSysUserId, item.getId()));

            List<String> roleNames = sysRoles.stream()
                    .map(it -> this.roleMapper.selectById(it.getRoleId()).getName())
                    .collect(Collectors.toList());

            downloadDTO.setRole(String.join(SConsts.EN_COMMA, roleNames));
            return downloadDTO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(SysUserSaveRequest request) {

        SysUserDO sysUser = this.convert(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(request.getPassword());
        sysUser.setPassword(password);
        Assert.isTrue(this.sysUserMapper.insert(sysUser) > 0, () -> "创建系统用户失败");
        modifySysUserRole(request.getRoleIds(), sysUser);
        return ResponseResult.toSuccess(sysUser.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(SysUserSaveRequest request) {
        SysUserDO sysUser = this.convert(request);
        Assert.isTrue(this.sysUserMapper.updateById(sysUser) > 0, () -> "更新用户失败");
        modifySysUserRole(request.getRoleIds(), sysUser);
        return ResponseResult.toSuccess(sysUser.getId());
    }

    @Override
    public ResponseResult<SysUserDTO> adminInfo(final String phone) {

        SysUserDO sysUser = this.sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getPhone, phone));

        Assert.isTrue(Objects.nonNull(sysUser), () -> ResponseStatus.USER_PHONE_NOT_EXISTS);

        List<String> permissions = this.menuHelper.queryRoleBySysUserId(sysUser.getId());

        // 所有访问菜单的路由
        List<MenuDO> menus = this.menuHelper.selectMenuBySysUserId(sysUser.getId());
        // 父级菜单
        List<MenuDTO> topMenu = Optional.ofNullable(menus).orElse(Collections.emptyList()).stream()
                .filter(menu -> IConsts.ZERO == LConsts.ZERO.compareTo(menu.getParentId()))
                .map(this::convert)
                .collect(Collectors.toList());
        // 这种写法是查出所有的菜单，移除顶级菜单，剩余的菜单就不是顶级菜单也就是parentId 都是不为null
        Map<Long, List<MenuDTO>> childrenMap = Optional.ofNullable(menus).orElse(Collections.emptyList()).stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.groupingBy(MenuDTO::getParentId, Collectors.toList()));
        // 整合成父子结构
        toTree(topMenu, childrenMap);

        return ResponseResult.toSuccess(SysUserDTO.builder()
                .phone(sysUser.getPhone())
                .name(sysUser.getName())
                .avatar(sysUser.getAvatar())
                .routers(topMenu)
                .permissions(permissions)
                .build());
    }

    @Override
    public ResponseResult<List<String>> getRolesBySysUserId(final Long sysUserId) {
        List<String> permissions = this.menuHelper.queryRoleBySysUserId(sysUserId);
        Assert.isTrue(CollectionUtils.isNotEmpty(permissions), () -> "没有权限访问");
        return ResponseResult.toSuccess(permissions);
    }

    @Override
    public ResponseResult<SysUserDTO> querySysUserByPhone(final String phone) {
        LambdaQueryWrapper<SysUserDO> wrapper = new LambdaQueryWrapper<SysUserDO>()
                .eq(SysUserDO::getPhone, phone);
        SysUserDO sysUserDO = this.sysUserMapper.selectOne(wrapper);
        Assert.isTrue(Objects.nonNull(sysUserDO), () -> "用户不存在");
        return ResponseResult.toSuccess(this.convert(sysUserDO));

    }

    // region 私有方法

    /**
     * 新增|修改
     */
    private void modifySysUserRole(List<Long> roleIds, SysUserDO sysUser) {

        // 查询系统用户与角色关系
        List<SysRoleDO> sysRoles = this.sysRoleMapper.selectList(new LambdaQueryWrapper<SysRoleDO>()
                .eq(SysRoleDO::getSysUserId, sysUser.getId()));
        if (CollectionUtils.isEmpty(roleIds)) {

            Optional.ofNullable(sysRoles).ifPresent(roles ->
                    this.sysRoleMapper.deleteBatchIds(roles.stream().map(SysRoleDO::getId).collect(Collectors.toList()))
            );
        } else {

            // 1. 当前用户拥有的角色主键
            List<Long> sysRoleIds = sysRoles.stream().map(SysRoleDO::getRoleId).collect(Collectors.toList());

            // 2. 若当前用户角色主键为空，则新增
            if (CollectionUtils.isEmpty(sysRoleIds)) {
                roleIds.forEach(roleId -> {
                    SysRoleDO sysRole = new SysRoleDO();
                    sysRole.setRoleId(roleId);
                    sysRole.setSysUserId(sysUser.getId());
                    sysRole.setDeleteStatus(Boolean.FALSE);
                    Assert.isTrue(this.sysRoleMapper.insert(sysRole) > 0, () -> "绑定角色失败");
                });
            } else { // 3. 若当前用户角色主键存在，则存在新增或者更新逻辑

                // 3.1 授权角色，不在当前用户已拥有的角色中，则新增
                roleIds.stream().filter(roleId -> !sysRoleIds.contains(roleId)).forEach(roleId -> {
                    SysRoleDO sysRole = new SysRoleDO();
                    sysRole.setRoleId(roleId);
                    sysRole.setSysUserId(sysUser.getId());
                    sysRole.setDeleteStatus(Boolean.FALSE);
                    Assert.isTrue(this.sysRoleMapper.insert(sysRole) > 0, () -> "绑定角色失败");
                });

                // 3.2 授权角色，不在当前用户已拥有的角色中，则删除已拥有的角色
                sysRoleIds.stream().filter(roleId -> !roleIds.contains(roleId)).forEach(roleId ->
                        Assert.isTrue(this.sysRoleMapper.delete(new LambdaQueryWrapper<SysRoleDO>()
                                .eq(SysRoleDO::getRoleId, roleId)
                                .eq(SysRoleDO::getSysUserId, sysUser.getId())) > 0, () -> "删除绑定角色失败")
                );


            }
        }
    }

    /**
     * 获取访问菜单
     *
     * @param menus       TOP菜单
     * @param childrenMap 孩子节点菜单
     */
    private void toTree(List<MenuDTO> menus, Map<Long, List<MenuDTO>> childrenMap) {
        menus.forEach(k -> {
            MenuDTO.Meta meta = new MenuDTO.Meta(k.getName(), k.getIcon(), k.getHidden(), Lists.newArrayList());
            k.setMeta(meta);
            List<MenuDTO> menuList = Optional.ofNullable(childrenMap.get(k.getId())).orElse(Collections.emptyList()).stream()
                    .filter(MenuDTO::getRouter)
                    .collect(Collectors.toList());
            toTree(menuList, childrenMap);
            k.setChildren(menuList);
        });
    }

    // endregion
}
