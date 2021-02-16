package quick.pager.pcloud.configuration;

import com.qcloud.cos.COS;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quick.pager.pcloud.properties.COSCloudProperties;
import quick.pager.pcloud.utils.Assert;


/**
 * OSS Auto {@link Configuration}.
 *
 * @author siguiyang
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "cos.cloud", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(COSCloudProperties.class)
@ImportAutoConfiguration(COSAutoConfiguration.class)
public class COSContextAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public COS cosClient(COSCloudProperties cosCloudProperties) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        Assert.isTrue(StringUtils.isNotEmpty(cosCloudProperties.getSecretId()),
                () -> "COS secret_id can't be empty.");
        Assert.isTrue(StringUtils.isNotEmpty(cosCloudProperties.getSecretKey()),
                () -> "COS access-key can't be empty.");

        Assert.isTrue(StringUtils.isNoneEmpty(cosCloudProperties.getRegionName()),
                () -> "COS region-name can't be empty.");

        COSCredentials cred = new BasicCOSCredentials(cosCloudProperties.getSecretId(), cosCloudProperties.getSecretKey());
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(cosCloudProperties.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }
}
