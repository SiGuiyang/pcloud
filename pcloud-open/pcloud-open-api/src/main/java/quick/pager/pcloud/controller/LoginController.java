package quick.pager.pcloud.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.open.request.OpenLoginRequest;
import quick.pager.pcloud.service.OpenLoginService;

/**
 * 登录服务
 */
@RestController
@RequestMapping("/open")
public class LoginController {

    @Resource
    private OpenLoginService openLoginService;

    /**
     * 开放平台登录
     *
     * @param request 请求体
     * @return 根据手机号码查询用户
     */
    @PostMapping("/login")
    public ResponseResult<OpenAccountDTO> login(@RequestBody OpenLoginRequest request) {
        return openLoginService.login(request);
    }
}
