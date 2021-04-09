package quick.pager.pcloud.filter;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.utils.WebUtils;
import reactor.core.publisher.Mono;

/**
 * 将登录用户全局过滤器
 *
 * @author siguiyang
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        String token = WebUtils.getHeader(httpRequest, HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(token) || token.startsWith("Basic")) {
            return chain.filter(exchange);
        }
        try {
            // 从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            Map map = JSON.parseObject(userStr, Map.class);
            // 解析用户登录信息
            String phone = (String) map.get("phone");
            String id = String.valueOf(map.get("id"));
            String name = (String) map.get("name");

            final String key = "pcloud:token:".concat(phone);
            // redis中不存在，则认为未登录，提示用户登录，登录过期提示
            Boolean hasKey = redisTemplate.hasKey(key);
            if (Objects.nonNull(hasKey) && !hasKey) {
                log.info("登录缓存token过期，提示用户重新登录 phone = {}", phone);
                return WebUtils.refuse(exchange, "登录过期");
            }

            // 判断token与redis存储的是否是一致的
            // 不一致说明第三方设备登录，那么之前登录的设备请求将会被强制踢下线
            // 若有如下判断逻辑，则不支持多端登录状态
            String redisToken = (String) redisTemplate.opsForValue().get(key);
            if (!realToken.equals(redisToken)) {
                log.info("token与redisToken不一致，phone = {}, token = {}, redisToken = {}", phone, realToken, redisToken);
                return WebUtils.refuse(exchange, "您的账号已在其它设备登录，请重新登录");
            }

            // 将手机号码放到消息头
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate()
                    .header("id", id)
                    .header("phone", phone)
                    .header("name", name)
                    .header("X-Real-IP", WebUtils.getRemoteAddr(httpRequest)).build())
                    .build());
        } catch (ParseException e) {
            log.error("token 解析失败");
        }
        return WebUtils.refuse(exchange, "登录过期");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
