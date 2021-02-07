package quick.pager.pcloud.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LogMQ {


    String SEND_LOG = "pcloud-request-log-input";

    @Input(SEND_LOG)
    SubscribableChannel sendLog();

}
