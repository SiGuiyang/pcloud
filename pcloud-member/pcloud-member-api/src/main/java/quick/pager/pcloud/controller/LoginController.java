package quick.pager.pcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberLoginRequest;

/**
 * 登录服务
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class LoginController {

    /**
     * 登录
     *
     * @param request 登录请求参数
     * @return 响应
     */
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody final MemberLoginRequest request) {
        return null;
    }


}
