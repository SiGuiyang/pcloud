package quick.pager.pcloud.controller;

import javax.annotation.Resource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.mq.OmsMq;

@RestController
@RequestMapping("/oms")
public class TestController {

    @Resource
    private OmsMq omsMq;


    /**
     * 延迟队列演示
     */
    @GetMapping("/mq/send")
    public String send() {

        boolean send = omsMq.orderSubmitDelayedOutput().send(MessageBuilder.withPayload("10000000").setHeader("x-delay", 1000 * 60).build());

        System.out.println(send);

        return "success";
    }
}
