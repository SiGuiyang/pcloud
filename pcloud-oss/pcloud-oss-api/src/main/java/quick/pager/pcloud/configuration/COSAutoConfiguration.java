package quick.pager.pcloud.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import quick.pager.pcloud.properties.COSCloudProperties;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(COSCloudProperties.class)
public class COSAutoConfiguration {
}
