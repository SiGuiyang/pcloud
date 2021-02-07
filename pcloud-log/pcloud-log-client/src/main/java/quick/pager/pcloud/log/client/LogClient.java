package quick.pager.pcloud.log.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.log.fallback.LogFallback;
import quick.pager.pcloud.log.request.LogSaveRequest;

/**
 * 日志服务
 *
 * @author siguiyang
 */
@FeignClient(value = "pcloud-log-api", path = "/log", fallbackFactory = LogFallback.class)
public interface LogClient {

    /**
     * 新增日志
     */
    @PostMapping("/action/create")
    ResponseResult<String> create(@RequestBody LogSaveRequest request);

}
