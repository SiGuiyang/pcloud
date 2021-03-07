package quick.pager.pcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 开放平台
 *
 * @author siguiyang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Open {


    public static void main(String[] args) {
        SpringApplication.run(Open.class, args);
    }
}
