package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.request.DemandAdminPageRequest;
import quick.pager.pcloud.request.DemandAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.DemandAdminVO;

/**
 * 需求管理
 *
 * @author siguiyang
 */
public interface DemandAdminService {

    /**
     * 广告管理
     *
     * @param request 请求参数
     */
    ResponseResult<List<DemandAdminVO>> page(final DemandAdminPageRequest request);

    /**
     * 保存
     *
     * @param request 请求参数
     */
    ResponseResult<Long> create(final DemandAdminSaveRequest request);

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    ResponseResult<Long> modify(final DemandAdminSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
