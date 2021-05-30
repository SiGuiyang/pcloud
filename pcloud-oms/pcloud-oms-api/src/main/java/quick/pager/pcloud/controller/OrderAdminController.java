package quick.pager.pcloud.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.request.OrderAdminPageRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.OrderAdminService;
import quick.pager.pcloud.vo.OrderAdminVO;

/**
 * 订单服务
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oms/admin")
public class OrderAdminController {

    @Resource
    private OrderAdminService orderAdminService;

    /**
     * 订单管理
     *
     * @param request 请求参数
     */
    @PostMapping("/page")
    public ResponseResult<List<OrderAdminVO>> page(@RequestBody final OrderAdminPageRequest request) {

        return orderAdminService.page(request);
    }
}
