package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.DeptDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.DeptOtherRequest;
import quick.pager.pcloud.request.DeptPageRequest;
import quick.pager.pcloud.request.DeptSaveRequest;

/**
 * 部门服务
 *
 * @author siguiyang
 */
public interface DeptService {

    /**
     * 查询列表
     */
    ResponseResult<List<DeptDTO>> queryPage(final DeptPageRequest request);

    /**
     * 查询列表，无分页
     */
    ResponseResult<List<DeptDTO>> queryList(final DeptOtherRequest request);

    /**
     * 部门树
     */
    List<DeptDTO> queryTree();

    /**
     * 新增
     */
    ResponseResult<Long> create(final DeptSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final DeptSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
