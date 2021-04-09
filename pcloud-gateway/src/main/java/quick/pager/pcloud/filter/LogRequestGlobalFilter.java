package quick.pager.pcloud.filter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.config.LogMQ;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 请求日志输出
 *
 * @author siguiyang
 */
@Component
@Slf4j
public class LogRequestGlobalFilter implements GlobalFilter, Ordered {

    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    @Resource
    private PathMatcher pathMatcher;
    @Resource
    private LogMQ logMQ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getPath().pathWithinApplication().value();

        // 后台打印日志
        if (pathMatcher.match("/admin/**", path)
                || pathMatcher.match("/*/admin/**", path)) {
            // get,delete 方法
            if (HttpMethod.GET.equals(request.getMethod())
                    || HttpMethod.DELETE.equals(request.getMethod())) {
                return chain.filter(exchange.mutate().request(request).build());
            } else {
                return readBody(exchange, chain);
            }
        }
        return chain.filter(exchange);
    }

    /**
     * readJsonBody
     */
    private Mono<Void> readBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                        DataBufferUtils.retain(buffer);
                        return Mono.just(buffer);
                    });
                    ServerHttpRequest mutatedRequest =
                            new ServerHttpRequestDecorator(request) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    return cachedFlux;
                                }
                            };
                    ServerWebExchange mutatedExchange =
                            exchange.mutate().request(mutatedRequest).build();
                    return ServerRequest.create(mutatedExchange, MESSAGE_READERS)
                            .bodyToMono(String.class)
                            .doOnNext(objectValue -> doLog(request, objectValue))
                            .then(chain.filter(mutatedExchange));
                });
    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 记录日志
     *
     * @param request 请求
     * @param params  请求参数
     */
    private void doLog(ServerHttpRequest request, String params) {
        UserAgent userAgent = UserAgentUtil.parse(request.getHeaders().getFirst("User-Agent"));
        Map<String, Object> map = Maps.newHashMap();
        map.put("params", params);
        map.put("name", request.getHeaders().getFirst("name"));
        map.put("userId", request.getHeaders().getFirst("id"));
        map.put("clientIp", request.getHeaders().getFirst("X-Real-IP"));
        map.put("path", request.getPath().pathWithinApplication().value());
        map.put("browser", userAgent.getBrowser().getName());
        map.put("os", userAgent.getOs().getName());

        try {
            boolean send = logMQ.sendLog().send(MessageBuilder.withPayload(JSON.toJSONString(map)).build());
            if (send) {
                log.info("log mq 发送成功！");
            }
        } catch (Exception e) {
            log.error("log mq 发送异常");
        }
    }
}
