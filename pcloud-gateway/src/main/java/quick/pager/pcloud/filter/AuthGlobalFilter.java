package quick.pager.pcloud.filter;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
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
        String token = WebUtils.getHeader(exchange.getRequest(), HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(token) || token.startsWith("Basic")) {
            return chain.filter(exchange);
        }
        try {
            // 从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            Map map = JSON.parseObject(userStr, Map.class);
            Map user = (Map) map.get("user");
            String phone = (String) user.get("phone");

            // redis中不存在，则认为未登录，提示用户登录，登录过期提示
            Boolean hasKey = redisTemplate.hasKey("pcloud:token:".concat(phone));
            if (Objects.nonNull(hasKey) && !hasKey) {
                log.info("登录缓存token过期，提示用户重新登录 phone = {}", phone);
                return WebUtils.refuse(exchange);
            }

            // 用户信息写入消息头
            if (MapUtils.isNotEmpty(user)) {
                // 将手机号码放到消息头
                ServerHttpRequest request = exchange.getRequest()
                        .mutate()
                        .header("id", String.valueOf(user.get("id")))
                        .header("phone", phone)
                        .header("name", (String) user.get("name"))
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            }
        } catch (ParseException e) {
            log.error("token 解析失败");
        }
        return WebUtils.refuse(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
