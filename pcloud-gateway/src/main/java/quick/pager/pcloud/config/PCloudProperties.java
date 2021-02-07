package quick.pager.pcloud.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = PCloudProperties.PCLOUD_PREFIX)
@Data
public class PCloudProperties {

    static final String PCLOUD_PREFIX = "pcloud";
    /**
     * 过滤输出请求地址
     */
    private List<String> skipLogUrls;

    /**
     * 接口白名单
     */
    private List<String> whiteUrls;

    /**
     * 登录接口白名单
     */
    private List<String> loginUrls;

}
