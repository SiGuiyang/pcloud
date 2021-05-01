package quick.pager.pcloud.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import quick.pager.pcloud.admin.client.AdminRoleClient;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.open.client.OpenAuthClient;
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
    private AdminRoleClient adminRoleClient;
    @Resource
    private OpenAuthClient openAuthClient;
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

        // 匹配开放平台
        if (pathMatcher.match("/open/**", path)
                && !pathMatcher.match("/open/admin/**", path)) {
            // 缓存取资源权限应用关系列表
            ResponseResult<Map<String, List<String>>> permissions = openAuthClient.permissions();
            Map<String, List<String>> openResourceMap = permissions.getData();
            List<String> authorities = this.getAuth(openResourceMap, path);

            return Mono.just(new AuthorizationDecision(CollectionUtils.isNotEmpty(authorities)));
        }

        // 非管理后台请求过来，直接通过
        if (!pathMatcher.match("/admin/**", path)
                && !pathMatcher.match("/*/admin/**", path)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        ResponseResult<Map<String, List<String>>> result = adminRoleClient.permission();
        Map<String, List<String>> resourceRolesMap = result.getData();

        // 以下是管理后台请求过来，需要验证接口权限
        if (MapUtils.isEmpty(resourceRolesMap)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 请求路径匹配到的资源需要的角色权限集合authorities统计
        List<String> authorities = this.getAuth(resourceRolesMap, path);

        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 匹配权限
     *
     * @param resourceMap 权限资源
     * @param path        访问接口
     */
    private List<String> getAuth(final Map<String, List<String>> resourceMap, final String path) {
        List<String> authorities = new ArrayList<>();
        resourceMap.forEach((pattern, v) -> {
            for (String p : v) {
                if (pathMatcher.match(p, path)) {
                    authorities.add(pattern);
                }
            }
        });
        return authorities;
    }
}
