package quick.pager.pcloud.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.request.SubmitRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.OrderSubmitVO;

/**
 * 订单服务
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oms")
public class OrderController {

    /**
     * 订单提交
     *
     * @param request 请求参数
     * @return 返回订单视图
     */
    @PostMapping("/order/submit")
    public ResponseResult<OrderSubmitVO> submit(@RequestBody SubmitRequest request) {
        return ResponseResult.toSuccess();
    }
}
