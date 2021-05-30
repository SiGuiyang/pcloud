package quick.pager.pcloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯云配置
 *
 * @author siguiyang
 */
@Data
@ConfigurationProperties(prefix = "cos.cloud")
public class COSCloudProperties {

    /**
     * 私钥Id
     */
    private String secretId;
    /**
     * 私钥key
     */
    private String secretKey;
    /**
     * 地区名
     */
    private String regionName;
    /**
     * 文件桶
     */
    private String bucket;
}
