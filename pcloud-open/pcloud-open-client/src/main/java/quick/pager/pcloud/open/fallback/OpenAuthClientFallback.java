package quick.pager.pcloud.open.fallback;

import org.springframework.stereotype.Component;
import quick.pager.pcloud.open.client.OpenAuthClient;
import quick.pager.pcloud.open.dto.UserDTO;
import quick.pager.pcloud.model.response.ResponseResult;

@Component
public class OpenAuthClientFallback implements OpenAuthClient {
    @Override
    public ResponseResult<UserDTO> login(String phone, String password) {
        return ResponseResult.toError("未找到用户");
    }
}
