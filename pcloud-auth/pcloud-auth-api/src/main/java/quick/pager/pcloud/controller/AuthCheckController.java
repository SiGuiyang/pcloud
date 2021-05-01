package quick.pager.pcloud.controller;

import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.response.ResponseResult;

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
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 验证token方法入口
     */
    @GetMapping("/token/check")
    public ResponseResult<String> checkToken(@RequestParam("token") String token,
                                             @RequestParam("phone") String phone,
                                             @RequestParam(value = "deviceId", required = false) String deviceId) {

        final String tokenKey = "pcloud:token:".concat(phone);
        // redis中不存在，则认为未登录，提示用户登录，登录过期提示
        Boolean hasKey = redisTemplate.hasKey(tokenKey);
        if (Objects.nonNull(hasKey) && !hasKey) {
            log.info("登录缓存token过期，提示用户重新登录 phone = {}", phone);

            return ResponseResult.toError(HttpStatus.UNAUTHORIZED.value(), "登录过期");
        }

        // 判断token与redis存储的是否是一致的
        // 不一致说明第三方设备登录，那么之前登录的设备请求将会被强制踢下线
        // 若有如下判断逻辑，则不支持多端登录状态
        String redisToken = (String) redisTemplate.opsForValue().get(tokenKey);
        if (!token.equals(redisToken)) {
            log.info("token与redisToken不一致，phone = {}, token = {}, redisToken = {}", phone, token, redisToken);
            return ResponseResult.toError(HttpStatus.UNAUTHORIZED.value(), "您的账号已在其它设备登录，请重新登录");
        }

        // 设备号处理
        if (StringUtils.isNotBlank(deviceId)) {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            final String deviceIdKey = "pcloud:deviceId:".concat(deviceId);
            Set<Object> members = opsForSet.members(deviceIdKey);

            if (CollectionUtils.isNotEmpty(members)
                    && members.size() >= 5
                    && members.contains(phone)) {
                return ResponseResult.toError(HttpStatus.UNAUTHORIZED.value(), "您的手机设备已经绑定5个账号");
            }

            opsForSet.add(deviceIdKey, phone);
        }

        return ResponseResult.toSuccess();
    }

}
