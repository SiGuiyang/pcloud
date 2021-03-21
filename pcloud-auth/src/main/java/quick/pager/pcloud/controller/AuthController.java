package quick.pager.pcloud.controller;

import com.alibaba.fastjson.JSON;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.admin.client.AdminAuthClient;
import quick.pager.pcloud.admin.dto.UserDTO;
import quick.pager.pcloud.dto.OAuthTokenDTO;
import quick.pager.pcloud.enums.GrantTypeEnums;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.LoginRequest;
import quick.pager.pcloud.open.client.OpenAuthClient;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.open.request.OpenLoginRequest;
import quick.pager.pcloud.service.JWTService;
import quick.pager.pcloud.utils.Assert;

/**
 * 登录授权
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private JWTService jwtService;
    @Resource
    private AdminAuthClient adminAuthClient;
    @Resource
    private OpenAuthClient openAuthClient;

    /**
     * 统一登录方法入口
     *
     * @param request 请求方法
     */
    @PostMapping("/token")
    public ResponseResult<OAuthTokenDTO> login(@RequestBody LoginRequest request) {

        Assert.isTrue(StringUtils.isNotEmpty(request.getGrantType()), () -> "登录授权方式错误");
        GrantTypeEnums grantTypeEnums = EnumUtils.getEnum(GrantTypeEnums.class, request.getGrantType());
        Assert.isTrue(Objects.nonNull(grantTypeEnums), () -> "登录授权方式未找到");

        switch (grantTypeEnums) {
            case ADMIN:
                return adminLogin(request);
            case OPEN:
                return openLogin(request);
            case H5:
            case APP:
            case ALIPAY:
            case WECHAT:
                return appLogin(request);
        }


        return ResponseResult.toError("登录失败");
    }

    /**
     * 管理平台登录
     *
     * @param request 请求参数
     */
    private ResponseResult<OAuthTokenDTO> adminLogin(final LoginRequest request) {

        ResponseResult<UserDTO> result = adminAuthClient.login(request.getPhone(), request.getPassword());

        if (result.check()) {
            UserDTO userDTO = result.getData();

            String token = jwtService.jwt(JSON.toJSONString(userDTO));

            OAuthTokenDTO tokenDTO = OAuthTokenDTO.builder()
                    .token(token)
                    .expiresIn(60 * 60 * 24 * 10)
                    .tokenHead("Bearer ").build();
            redisTemplate.opsForValue().set("pcloud:token:" + request.getPhone(), tokenDTO.getToken(), 10, TimeUnit.DAYS);

            return ResponseResult.toSuccess(tokenDTO);
        }

        return ResponseResult.toError(result.getMsg());
    }

    /**
     * 开放平台登录
     *
     * @param request 请求参数
     */
    private ResponseResult<OAuthTokenDTO> openLogin(final LoginRequest request) {

        OpenLoginRequest openLoginReq = new OpenLoginRequest();
        openLoginReq.setSecureId(request.getSecureId());
        openLoginReq.setSecureKey(request.getSecureKey());

        ResponseResult<OpenAccountDTO> result = openAuthClient.login(openLoginReq);

        if (result.check()) {
            OpenAccountDTO openAccountDTO = result.getData();
            String token = jwtService.jwt(JSON.toJSONString(openAccountDTO));

            OAuthTokenDTO tokenDTO = OAuthTokenDTO.builder()
                    .token(token)
                    .expiresIn(60 * 60 * 24 * 10)
                    .tokenHead("Bearer ").build();
            redisTemplate.opsForValue().set("pcloud:open:token:" + request.getSecureId(), tokenDTO.getToken(), 2, TimeUnit.HOURS);

            return ResponseResult.toSuccess(tokenDTO);
        }

        return ResponseResult.toError(result.getMsg());
    }

    /**
     * 移动端登录
     *
     * @param request 请求参数
     */
    private ResponseResult<OAuthTokenDTO> appLogin(final LoginRequest request) {
        return null;
    }

}
