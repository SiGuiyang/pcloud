package quick.pager.pcloud.oss.fallback;

import com.alibaba.fastjson.JSON;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.oss.client.OSSClient;
import quick.pager.pcloud.oss.request.OSSUploadRequest;
import quick.pager.pcloud.response.ResponseResult;

@Component
@Slf4j
public class OSSClientFallback implements OSSClient {

    @Override
    public ResponseResult<Map<String, String>> upload(final OSSUploadRequest request) {
        log.error("服务熔断 path = {}, req = {}", "/upload/post", JSON.toJSONString(request));
        return ResponseResult.toError(ResponseStatus.FAIL_CODE, ResponseStatus.FALLBACK_MSG);
    }
}
