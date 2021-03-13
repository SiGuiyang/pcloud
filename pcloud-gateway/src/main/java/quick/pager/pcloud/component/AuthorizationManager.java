package quick.pager.pcloud.component;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import quick.pager.pcloud.constants.SConsts;
import quick.pager.pcloud.utils.WebUtils;
import reactor.core.publisher.Mono;

/**
 * 鉴权管理器
 *
 * @author siguiyang
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PathMatcher pathMatcher;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getURI().getPath();

        // token为空拒绝访问
        String token = WebUtils.getHeader(request, HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 缓存取资源权限角色关系列表
        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(SConsts.AUTHORITY_PREFIX);

        // 非管理后台请求过来，直接通过
        if (!pathMatcher.match("/admin/**", path)
                && !pathMatcher.match("/*/admin/**", path)) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 以下是管理后台请求过来，需要验证接口权限
        if (MapUtils.isEmpty(resourceRolesMap)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 请求路径匹配到的资源需要的角色权限集合authorities统计
        List<String> authorities = new ArrayList<>();
        resourceRolesMap.forEach((pattern, v) -> {
            List<String> paths = JSON.parseArray(v.toString(), String.class);
            for (String p : paths) {
                if (pathMatcher.match(p, path)) {
                    authorities.add((String) pattern);
                }
            }

        });

        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
