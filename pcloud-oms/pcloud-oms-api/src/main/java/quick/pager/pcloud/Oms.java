package quick.pager.pcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import quick.pager.pcloud.annotation.EnableXxlJobAutoConfiguration;

/**
 * @author siguiyang
 */
@SpringCloudApplication
@EnableFeignClients
@EnableXxlJobAutoConfiguration
public class Oms {

    public static void main(String[] args) {
        SpringApplication.run(Oms.class, args);
    }
}
