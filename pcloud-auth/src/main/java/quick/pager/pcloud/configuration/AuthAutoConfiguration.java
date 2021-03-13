package quick.pager.pcloud.configuration;

import java.security.KeyPair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import quick.pager.pcloud.service.JWTService;

/**
 * Auth 自动配置
 *
 * @author siguiyang
 */
@Configuration
public class AuthAutoConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("pcloud.jks"), "pcloud".toCharArray());
        return keyStoreKeyFactory.getKeyPair("pcloud", "pcloud".toCharArray());
    }

    @Bean
    public JWTService jwtService(KeyPair keyPair) {
        return new JWTService(keyPair);
    }
}
