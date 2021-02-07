package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.PermissionDTO;
import quick.pager.pcloud.dto.GradeDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.GradeOtherRequest;
import quick.pager.pcloud.request.GradePageRequest;
import quick.pager.pcloud.request.GradeSaveRequest;
import quick.pager.pcloud.request.RoleOtherRequest;
import quick.pager.pcloud.request.RolePageRequest;
import quick.pager.pcloud.request.RoleSaveRequest;

/**
 * 职级服务
 *
 * @author siguiyang
 */
public interface GradeService {

    /**
     * 查询列表
     */
    ResponseResult<List<GradeDTO>> queryPage(final GradePageRequest request);

    /**
     * 查询列表，无分页
     */
    ResponseResult<List<GradeDTO>> queryList(final GradeOtherRequest request);

    /**
     * 新增
     */
    ResponseResult<Long> create(final GradeSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final GradeSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);

}
