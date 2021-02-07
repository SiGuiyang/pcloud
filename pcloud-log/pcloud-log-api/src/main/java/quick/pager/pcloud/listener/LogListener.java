package quick.pager.pcloud.listener;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.log.request.LogSaveRequest;
import quick.pager.pcloud.mq.LogMQ;
import quick.pager.pcloud.service.LogService;

/**
 * 记录请求日志
 *
 * @author siguiyang
 */
@Component
public class LogListener {

    @Resource
    private LogService logService;

    @StreamListener(target = LogMQ.SEND_LOG)
    public void process(@Payload String msg) throws IOException {
        LogSaveRequest logSaveReq = JSON.parseObject(msg, LogSaveRequest.class);

        logService.create(logSaveReq);

    }
}
