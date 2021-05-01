package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.LeafDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.IdGenPageRequest;
import quick.pager.pcloud.request.IdGenSaveRequest;

/**
 * id生成¬号段管理服务
 *
 * @author siguiyang
 */
public interface SegmentService {

    /**
     * 新增
     *
     * @param request 请求参数
     */
    ResponseResult<String> create(final IdGenSaveRequest request);

    /**
     * 修改
     *
     * @param request 请求参数
     */
    ResponseResult<String> modify(final IdGenSaveRequest request);

    /**
     * 列表分页
     *
     * @param request 请求参数
     */
    ResponseResult<List<LeafDTO>> page(final IdGenPageRequest request);
}
