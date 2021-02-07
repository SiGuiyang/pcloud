package quick.pager.pcloud.component;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.utils.WebUtils;
import reactor.core.publisher.Mono;


/**
 * 没有权限访问时
 *
 * @author siguiyang
 */
@Component
public class AccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = WebUtils.getDataBuffer(response, HttpStatus.FORBIDDEN, "没有权限访问");
        return response.writeWith(Mono.just(buffer));
    }
}
