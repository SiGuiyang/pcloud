package quick.pager.pcloud.auth.fallback;

import org.springframework.stereotype.Component;
import quick.pager.pcloud.auth.client.AuthClient;
import quick.pager.pcloud.response.ResponseResult;

@Component
public class AuthClientFallback implements AuthClient {
    @Override
    public ResponseResult<String> checkToken(String token, String phone, String deviceId) {
        return null;
    }
}
