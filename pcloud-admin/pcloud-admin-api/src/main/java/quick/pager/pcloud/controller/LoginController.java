package quick.pager.pcloud.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.LoginService;

/**
 * 系统管理登录
 *
 * @author siguiyang
 * @version 3.0
 */
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 获取系统用户
     *
     * @param phone 手机号码
     */
    @PostMapping("/permit/login")
    public ResponseResult<SysUserDTO> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return loginService.login(phone, password);
    }

}
