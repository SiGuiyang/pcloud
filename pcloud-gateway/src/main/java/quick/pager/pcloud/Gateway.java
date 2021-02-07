package quick.pager.pcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import quick.pager.pcloud.config.LogMQ;

/**
 * 启动类
 *
 * @author siguiyang
 */
@SpringCloudApplication
@EnableBinding(LogMQ.class)
public class Gateway {

    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }
}

