package quick.pager.pcloud.component;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.utils.WebUtils;
import reactor.core.publisher.Mono;

/**
 * 登录过期
 *
 * @author siguiyang
 */
@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = WebUtils.getDataBuffer(response, HttpStatus.UNAUTHORIZED, "登录过期");
        return response.writeWith(Mono.just(buffer));
    }
}
