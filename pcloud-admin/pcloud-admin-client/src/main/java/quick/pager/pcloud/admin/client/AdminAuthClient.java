package quick.pager.pcloud.admin.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import quick.pager.pcloud.admin.dto.UserDTO;
import quick.pager.pcloud.admin.fallback.AdminAuthClientFallback;
import quick.pager.pcloud.response.ResponseResult;

@FeignClient(value = "pcloud-admin-api", path = "/admin", fallback = AdminAuthClientFallback.class)
public interface AdminAuthClient {

    /**
     * 获取系统用户
     *
     * @param phone    手机号码
     * @param password 密码
     * @return 根据手机号码查询用户
     */
    @RequestMapping(value = "/permit/login", method = RequestMethod.POST)
    ResponseResult<UserDTO> login(@RequestParam("phone") String phone, @RequestParam("password") String password);

    /**
     * 根据系统用户Id查询此用户所拥有的角色
     *
     * @param sysUserId 系统用户Id
     * @return 返回当前系统登陆用户拥有的权限
     */
    @RequestMapping(value = "/permit/permission/{sysUserId}", method = RequestMethod.POST)
    ResponseResult<List<String>> getRolesBySysUserId(@PathVariable("sysUserId") Long sysUserId);

}
