package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.PermissionDTO;
import quick.pager.pcloud.dto.RoleDTO;
import quick.pager.pcloud.request.RoleOtherRequest;
import quick.pager.pcloud.request.RolePageRequest;
import quick.pager.pcloud.request.RoleSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.RoleService;
import quick.pager.pcloud.utils.Assert;

/**
 * 角色管理
 *
 * @author siguiyang
 * @version 3.0
 */
@RestController
@RequestMapping("/admin")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 获取系统角色
     */
    @PostMapping("/role/page")
    public ResponseResult<List<RoleDTO>> page(@RequestBody RolePageRequest request) {
        return roleService.queryPage(request);
    }

    /**
     * 获取系统角色
     */
    @PostMapping("/role/list")
    public ResponseResult<List<RoleDTO>> list(@RequestBody RoleOtherRequest request) {
        return roleService.queryList(request);
    }


    /**
     * 新增系统角色
     */
    @PostMapping("/role/create")
    public ResponseResult<Long> create(@RequestBody RoleSaveRequest request) {
        return roleService.create(request);
    }

    /**
     * 修改系统角色
     */
    @PutMapping("/role/modify")
    public ResponseResult<Long> modify(@RequestBody RoleSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return roleService.modify(request);
    }

    /**
     * 更新角色全量缓存
     */
    @GetMapping("/role/refresh")
    public ResponseResult<String> refresh() {
        return roleService.refresh();
    }

    /**
     * 更新角色全量
     */
    @GetMapping("/role/permission")
    public ResponseResult<Map<String, List<String>>> permission() {
        return roleService.permission();
    }

    /**
     * 删除角色
     *
     * @param id 角色主键
     */
    @DeleteMapping("/role/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return roleService.delete(id);
    }

    /**
     * 查看某个系统角色的权限列表
     */
    @GetMapping("/role/{roleId}/menu")
    public ResponseResult<PermissionDTO> rolePermission(@PathVariable("roleId") Long roleId) {
        return roleService.rolePermission(roleId);
    }
}
