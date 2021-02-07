package quick.pager.pcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import quick.pager.pcloud.mq.LogMQ;

/**
 * @author siguiyang
 */
@SpringCloudApplication
@EnableFeignClients
@EnableBinding(LogMQ.class)
public class Log {

    public static void main(String[] args) {
        SpringApplication.run(Log.class, args);
    }

}
