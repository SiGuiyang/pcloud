package quick.pager.pcloud.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * OMS 订单MQ中心
 *
 * @author siguiyang
 */
public interface OmsMq {

    /**
     * 订单提交未支付生产者
     */
    String ORDER_SUBMIT_DELAYED_OUTPUT = "orderSubmitDelayedOutput";
    /**
     * 订单提交未支付消费者
     */
    String ORDER_SUBMIT_DELAYED_INPUT = "orderSubmitDelayedInput";


    @Output(ORDER_SUBMIT_DELAYED_OUTPUT)
    MessageChannel orderSubmitDelayedOutput();

    @Input(ORDER_SUBMIT_DELAYED_INPUT)
    SubscribableChannel orderSubmitDelayedInput();
}
