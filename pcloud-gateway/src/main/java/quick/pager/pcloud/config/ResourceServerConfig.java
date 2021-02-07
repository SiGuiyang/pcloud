package quick.pager.pcloud.config;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import quick.pager.pcloud.component.AccessDeniedHandler;
import quick.pager.pcloud.component.AuthenticationEntryPoint;
import quick.pager.pcloud.component.AuthorizationManager;
import quick.pager.pcloud.constants.SConsts;
import quick.pager.pcloud.filter.IgnoreUrlsRemoveJwtFilter;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 *
 * @author siguiyang
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private final AuthorizationManager authorizationManager;
    private final PCloudProperties pCloudProperties;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        //自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint);
        //对白名单路径，直接移除JWT请求头
        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        List<String> permit = Lists.newArrayList();

        permit.addAll(pCloudProperties.getLoginUrls());
        permit.addAll(pCloudProperties.getWhiteUrls());

        http.authorizeExchange()
                // 白名单配置
                .pathMatchers(ArrayUtil.toArray(permit, String.class)).permitAll()
                // 鉴权管理器配置
                .anyExchange().access(authorizationManager)
                .and().exceptionHandling()
                // 处理未授权
                .accessDeniedHandler(accessDeniedHandler)
                // 处理未认证
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(SConsts.EMPTY);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(SConsts.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
