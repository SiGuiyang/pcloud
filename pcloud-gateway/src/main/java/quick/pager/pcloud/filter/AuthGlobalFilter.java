package quick.pager.pcloud.filter;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import java.text.ParseException;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.auth.client.AuthClient;
import quick.pager.pcloud.response.ResponseResult;
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
    private AuthClient authClient;

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

            ResponseResult<String> result = authClient.checkToken(realToken, phone, WebUtils.getHeader(httpRequest, "deviceId"));

            if (!result.check()) {
                return WebUtils.refuse(exchange, result.getMsg());
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
