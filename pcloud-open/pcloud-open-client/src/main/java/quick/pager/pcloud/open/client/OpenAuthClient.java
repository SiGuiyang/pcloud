package quick.pager.pcloud.open.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.open.fallback.OpenAuthClientFallback;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.open.request.OpenLoginRequest;

@FeignClient(value = "pcloud-open-api", path = "/open", fallback = OpenAuthClientFallback.class)
public interface OpenAuthClient {

    /**
     * 开放平台登录
     *
     * @param request 请求体
     * @return 根据手机号码查询用户
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseResult<OpenAccountDTO> login(@RequestBody OpenLoginRequest request);

    /**
     * 访问权限
     */
    @GetMapping("/resource/permissions")
    ResponseResult<Map<String, List<String>>> permissions();
}
