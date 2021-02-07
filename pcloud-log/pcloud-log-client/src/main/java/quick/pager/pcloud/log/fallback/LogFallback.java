package quick.pager.pcloud.log.fallback;

import com.alibaba.fastjson.JSON;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.log.client.LogClient;
import quick.pager.pcloud.log.request.LogSaveRequest;

@Component
@Slf4j
public class LogFallback implements FallbackFactory<LogClient> {
    @Override
    public LogClient create(Throwable cause) {
        return new LogClient() {
            @Override
            public ResponseResult<String> create(LogSaveRequest request) {
                log.info("熔断请求参数 {}", JSON.toJSONString(request));
                return ResponseResult.toError(444, "请勿重复提交");
            }
        };
    }
}
