package quick.pager.pcloud.service;

import java.util.List;
import java.util.Map;
import quick.pager.pcloud.dto.PermissionDTO;
import quick.pager.pcloud.dto.RoleDTO;
import quick.pager.pcloud.request.RoleOtherRequest;
import quick.pager.pcloud.request.RolePageRequest;
import quick.pager.pcloud.request.RoleSaveRequest;
import quick.pager.pcloud.response.ResponseResult;

/**
 * 角色服务
 *
 * @author siguiyang
 * @version 3.0
 */
public interface RoleService {

    /**
     * 查询列表
     */
    ResponseResult<List<RoleDTO>> queryPage(final RolePageRequest request);

    /**
     * 查询列表，无分页
     */
    ResponseResult<List<RoleDTO>> queryList(final RoleOtherRequest request);

    /**
     * 新增
     */
    ResponseResult<Long> create(final RoleSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final RoleSaveRequest request);

    /**
     * 更新全量角色缓存
     */
    ResponseResult<String> refresh();

    /**
     * 角色权限
     */
    ResponseResult<Map<String, List<String>>> permission();

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);

    /**
     * 查询角色的权限
     *
     * @param roleId 角色主键
     */
    ResponseResult<PermissionDTO> rolePermission(final Long roleId);
}
