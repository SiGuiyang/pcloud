package quick.pager.pcloud.filter;

import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.config.PCloudProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应结果日志输出
 *
 * @author siguiyang
 */
@Component
@Slf4j
public class LogResponseGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private PCloudProperties pCloudProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getPath().pathWithinApplication().value();

        if (!pCloudProperties.getSkipLogUrls().contains(path)) {

            DataBufferFactory bufferFactory = response.bufferFactory();
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            log.info("Response INFO = {}", new String(content, StandardCharsets.UTF_8));
                            return bufferFactory.wrap(content);
                        }));
                    }
                    return super.writeWith(body);
                }
            };

            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }

}
