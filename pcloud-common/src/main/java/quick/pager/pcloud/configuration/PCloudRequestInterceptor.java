package quick.pager.pcloud.configuration;

import com.google.common.collect.Maps;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;

/**
 * feign接口消息头处理
 *
 * @author siguiyang
 */
@Configuration
public class PCloudRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Map<String, String> map = PCloudRequestHolder.get();
        Optional.ofNullable(map).orElseGet(Maps::newHashMap).forEach(template::header);
    }
}
