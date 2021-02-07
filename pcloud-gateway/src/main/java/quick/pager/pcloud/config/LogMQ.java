package quick.pager.pcloud.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 管理后台日志MQ
 *
 * @author siguiyang
 */
public interface LogMQ {

    /**
     * 发送消息
     */
    @Output("pcloud-request-log-output")
    MessageChannel sendLog();
}
