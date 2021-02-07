package quick.pager.pcloud.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 网关配置
 *
 * @author siguiyang
 */
@Configuration
@EnableConfigurationProperties(PCloudProperties.class)
public class PCloudGatewayConfiguration {

    @Bean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }
}
