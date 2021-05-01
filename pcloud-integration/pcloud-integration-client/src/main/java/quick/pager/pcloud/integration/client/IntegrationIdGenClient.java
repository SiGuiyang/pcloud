package quick.pager.pcloud.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import quick.pager.pcloud.response.ResponseResult;

/**
 * 请求Id生产器服务
 *
 * @author siguiyang
 */
@FeignClient(value = "pcloud-integration-api", path = "/integration")
public interface IntegrationIdGenClient {


    /**
     * 请求Id生产器服务
     * 获取当前Id号段值
     *
     * @param key 业务号段
     */
    @GetMapping(value = "/segment/get/{key}")
    ResponseResult<String> getSegmentId(@PathVariable("key") String key);
}
