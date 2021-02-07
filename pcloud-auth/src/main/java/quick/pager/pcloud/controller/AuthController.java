package quick.pager.pcloud.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.dto.OAuthTokenDTO;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 登录授权
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/token")
    public ResponseResult<OAuthTokenDTO> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();

        if (Objects.nonNull(oAuth2AccessToken)) {
            OAuthTokenDTO tokenDTO = OAuthTokenDTO.builder()
                    .token(oAuth2AccessToken.getValue())
                    .expiresIn(oAuth2AccessToken.getExpiresIn())
                    .tokenHead("Bearer ").build();
            redisTemplate.opsForValue().set("pcloud:token:" + parameters.get("username"), tokenDTO.getToken(), 10, TimeUnit.DAYS);
            return ResponseResult.toSuccess(tokenDTO);
        } else {
            return ResponseResult.toError("登录授权失败");
        }
    }
}
