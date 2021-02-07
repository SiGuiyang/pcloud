package quick.pager.pcloud.configuration;

import com.google.common.collect.Lists;
import java.security.KeyPair;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import quick.pager.pcloud.service.OAuthUserService;

/**
 * OAuth security 配置
 *
 * @author siguiyang
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2SecurityConfiguration extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private OAuthUserService oAuthUserService;
    private PasswordEncoder passwordEncoder;

    public OAuth2SecurityConfiguration(AuthenticationManager authenticationManager,
                                       OAuthUserService oAuthUserService,
                                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.oAuthUserService = oAuthUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("pcloud")
                .secret(passwordEncoder.encode("pcloud"))
                .scopes("all")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(60 * 60 * 24 * 10);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = Lists.newArrayList();
        delegates.add(accessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates);

        endpoints
                .userDetailsService(oAuthUserService)
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(enhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtTokenConverter accessTokenConverter() {
        JwtTokenConverter jwtAccessTokenConverter = new JwtTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("pcloud.jks"), "pcloud".toCharArray());
        return keyStoreKeyFactory.getKeyPair("pcloud", "pcloud".toCharArray());
    }
}
