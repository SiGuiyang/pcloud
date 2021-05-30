package quick.pager.pcloud.oss.client;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import quick.pager.pcloud.oss.fallback.OSSClientFallback;
import quick.pager.pcloud.oss.request.OSSUploadRequest;
import quick.pager.pcloud.response.ResponseResult;

@FeignClient(value = "pcloud-oss-api", path = "/oss", fallback = OSSClientFallback.class)
public interface OSSClient {

    /**
     * 普通文本上传
     *
     * @param request 请求参数
     */
    @PostMapping("/upload/post")
    ResponseResult<Map<String, String>> upload(@RequestBody OSSUploadRequest request);
}
