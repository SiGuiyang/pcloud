package quick.pager.pcloud.utils;

import com.alibaba.fastjson.JSON;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import quick.pager.pcloud.model.response.ResponseResult;
import reactor.core.publisher.Mono;

/**
 * gateway 工具类
 *
 * @author siguiyang
 */
public class WebUtils {

    /**
     * 获取请求体中的字符串内容
     */
    public static String resolveBodyFromRequest(ServerHttpRequest request) {
        StringBuilder sb = new StringBuilder();

        request.getBody().subscribe(buffer -> {
            buffer.asInputStream();
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });

        return sb.toString();
    }

    /**
     * 获取请求参数
     *
     * @param request request 请求对象
     * @param key     请求参数的key
     * @return 参数内容
     */
    public static String getParameter(ServerHttpRequest request, String key) {
        return request.getQueryParams().getFirst(key);
    }

    /**
     * 获取请求参数
     *
     * @param request request 请求对象
     * @param key     请求参数的key
     * @return 参数内容
     */
    public static String getHeader(ServerHttpRequest request, String key) {
        return request.getHeaders().getFirst(key);
    }


    /**
     * 数据返回缓存值
     *
     * @param response   ServerHttpResponse
     * @param httpStatus httpStatus状态码
     * @param errorMsg   错误内容
     * @return DataBuffer
     */
    public static DataBuffer getDataBuffer(final ServerHttpResponse response, final HttpStatus httpStatus, final String errorMsg) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body = JSON.toJSONString(ResponseResult.toError(httpStatus.value(), errorMsg));
        return response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 网关拒绝，返回Result
     */
    public static Mono<Void> refuse(ServerWebExchange serverWebExchange) {
        // 权限不够拦截
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        ResponseResult data = ResponseResult.toError(HttpStatus.UNAUTHORIZED.value(), "登录过期");
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8));
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(buffer));

    }
}
