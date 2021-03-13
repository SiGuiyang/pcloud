package quick.pager.pcloud.open.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import quick.pager.pcloud.open.dto.UserDTO;
import quick.pager.pcloud.open.fallback.AdminAuthClientFallback;
import quick.pager.pcloud.model.response.ResponseResult;

@FeignClient(value = "pcloud-open-api", path = "/open", fallback = AdminAuthClientFallback.class)
public interface OpenAuthClient {

    /**
     * 获取系统用户
     *
     * @param phone    手机号码
     * @param password 密码
     * @return 根据手机号码查询用户
     */
    @RequestMapping(value = "/permit/login", method = RequestMethod.POST)
    ResponseResult<UserDTO> login(@RequestParam("phone") String phone, @RequestParam("password") String password);
}
