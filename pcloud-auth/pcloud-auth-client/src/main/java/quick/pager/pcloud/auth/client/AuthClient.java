package quick.pager.pcloud.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quick.pager.pcloud.auth.fallback.AuthClientFallback;
import quick.pager.pcloud.response.ResponseResult;

@FeignClient(value = "pcloud-auth-api", path = "/oauth", fallback = AuthClientFallback.class)
public interface AuthClient {

    /**
     * 验证token机制
     *
     * @param token token
     */
    @GetMapping("/token/check")
    ResponseResult<String> checkToken(@RequestParam("token") String token,
                                      @RequestParam("phone") String phone,
                                      @RequestParam(value = "deviceId", required = false) String deviceId);
}
