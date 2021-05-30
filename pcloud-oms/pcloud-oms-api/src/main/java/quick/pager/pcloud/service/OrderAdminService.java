package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.request.OrderAdminPageRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.OrderAdminVO;

/**
 * 订单服务
 *
 * @author siguiyang
 */
public interface OrderAdminService {
    /**
     * 订单管理
     *
     * @param request 请求参数
     */
    ResponseResult<List<OrderAdminVO>> page(final OrderAdminPageRequest request);
}
