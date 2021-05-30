package quick.pager.pcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import quick.pager.pcloud.annotation.EnableXxlJobAutoConfiguration;

/**
 * @author siguiyang
 */
@SpringCloudApplication
@EnableXxlJobAutoConfiguration
@EnableFeignClients
public class Msg {

    public static void main(String[] args) {
        SpringApplication.run(Msg.class, args);
    }
}
