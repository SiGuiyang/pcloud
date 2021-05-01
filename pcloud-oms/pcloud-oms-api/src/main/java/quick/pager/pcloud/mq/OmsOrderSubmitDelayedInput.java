package quick.pager.pcloud.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OmsOrderSubmitDelayedInput {


    @StreamListener(OmsMq.ORDER_SUBMIT_DELAYED_INPUT)
    public void payload(@Payload String payload) {

        log.info("mq 接收消息 {}", payload);

    }
}
