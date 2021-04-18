package quick.pager.pcloud.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.model.request.LogSaveRequest;
import quick.pager.pcloud.mq.LogMQ;
import quick.pager.pcloud.service.LogService;

/**
 * 记录请求日志
 *
 * @author siguiyang
 */
@Component
@Slf4j
public class LogListener {

    @Resource
    private LogService logService;

    @StreamListener(target = LogMQ.SEND_LOG)
    public void process(@Payload String msg,
                        @Header(AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        log.info("消费请求日志 msg = {}", msg);
        LogSaveRequest logSaveReq = JSON.parseObject(msg, LogSaveRequest.class);

        logService.create(logSaveReq);
        channel.basicAck(deliveryTag, false);

    }
}
