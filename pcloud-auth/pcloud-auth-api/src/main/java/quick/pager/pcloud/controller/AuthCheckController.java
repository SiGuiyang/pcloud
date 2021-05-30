package quick.pager.pcloud.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.AuthCheckService;

/**
 * checkToken
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthCheckController {

    @Resource
    private AuthCheckService authCheckService;

    /**
     * 验证token方法入口
     */
    @GetMapping("/token/check")
    public ResponseResult<String> checkToken(@RequestParam("token") String token,
                                             @RequestParam("phone") String phone,
                                             @RequestParam(value = "deviceId", required = false) String deviceId) {
        return authCheckService.checkToken(token, phone, deviceId);
    }

}
