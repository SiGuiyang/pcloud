package quick.pager.pcloud.configuration;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.dto.OAuthUserDTO;

/**
 * JWT 增强器
 *
 * @author siguiyang
 */
@Component
public class JwtTokenConverter extends JwtAccessTokenConverter {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuthUserDTO) {
            OAuthUserDTO authUserDTO = (OAuthUserDTO) principal;
            Map<String, Object> user = Maps.newHashMap();
            user.put("id", authUserDTO.getId());
            user.put("phone", authUserDTO.getUsername());
            user.put("name", authUserDTO.getName());
            user.put("avatar", authUserDTO.getAvatar());
            info.put("user", user);
        }
        result.setAdditionalInformation(info);
        result.setValue(encode(result, authentication));
        return result;
    }
}
