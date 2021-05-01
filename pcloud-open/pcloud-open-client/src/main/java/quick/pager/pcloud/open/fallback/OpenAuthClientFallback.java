package quick.pager.pcloud.open.fallback;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.open.client.OpenAuthClient;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.open.request.OpenLoginRequest;

@Component
public class OpenAuthClientFallback implements OpenAuthClient {
    @Override
    public ResponseResult<OpenAccountDTO> login(OpenLoginRequest request) {
        return ResponseResult.toError("未找到用户");
    }

    @Override
    public ResponseResult<Map<String, List<String>>> permissions() {
        return ResponseResult.toError("没有权限");
    }
}
