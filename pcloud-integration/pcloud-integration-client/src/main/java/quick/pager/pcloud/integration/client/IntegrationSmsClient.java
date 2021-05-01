package quick.pager.pcloud.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import quick.pager.pcloud.integration.request.SmsRequest;
import quick.pager.pcloud.response.ResponseResult;

/**
 * 发送短信
 *
 * @author siguiyang
 */
@FeignClient(value = "pcloud-integration-api", path = "/integration")
public interface IntegrationSmsClient {


    /**
     * 发送短信
     *
     * @param request 请求参数
     */
    @PostMapping("/sms/send")
    ResponseResult<String> send(@RequestBody final SmsRequest request);
}
