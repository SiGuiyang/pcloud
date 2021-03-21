package quick.pager.pcloud.open.fallback;

import org.springframework.stereotype.Component;
import quick.pager.pcloud.open.client.OpenAuthClient;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.open.request.OpenLoginRequest;

@Component
public class OpenAuthClientFallback implements OpenAuthClient {
    @Override
    public ResponseResult<OpenAccountDTO> login(OpenLoginRequest request) {
        return ResponseResult.toError("未找到用户");
    }
}
