package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.request.AdAdminPageRequest;
import quick.pager.pcloud.request.AdAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.AdAdminVO;

/**
 * 广告服务
 *
 * @author siguiyang
 */
public interface AdAdminService {

    /**
     * 广告管理
     *
     * @param request 请求参数
     */
    ResponseResult<List<AdAdminVO>> page(final AdAdminPageRequest request);

    /**
     * 保存
     *
     * @param request 请求参数
     */
    ResponseResult<Long> create(final AdAdminSaveRequest request);

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    ResponseResult<Long> modify(final AdAdminSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);

}
