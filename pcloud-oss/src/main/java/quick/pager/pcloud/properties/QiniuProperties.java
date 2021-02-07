package quick.pager.pcloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 七牛配置参数
 *
 * @author siguiyang
 * @version 3.0
 */
@ConfigurationProperties(value = "qiniu")
@Data
public class QiniuProperties {
    /**
     * 访问key
     */
    private String accessKey;
    /**
     * 访问密钥
     */
    private String secretKey;
    /**
     * 桶
     */
    private String bucket;
    /**
     * 访问地址
     */
    private String endpoint;
    /**
     * 是否启用
     */
    private Boolean enable;
}
