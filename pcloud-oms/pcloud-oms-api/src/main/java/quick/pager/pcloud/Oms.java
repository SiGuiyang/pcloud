package quick.pager.pcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import quick.pager.pcloud.annotation.EnableXxlJobAutoConfiguration;
import quick.pager.pcloud.mq.OmsMq;

/**
 * @author siguiyang
 */
@SpringCloudApplication
@EnableFeignClients
@EnableXxlJobAutoConfiguration
@EnableBinding(OmsMq.class)
public class Oms {

    public static void main(String[] args) {
        SpringApplication.run(Oms.class, args);
    }
}
