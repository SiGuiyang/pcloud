package quick.pager.pcloud.admin.fallback;

import java.util.List;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.admin.client.AdminAuthClient;
import quick.pager.pcloud.admin.dto.UserDTO;
import quick.pager.pcloud.response.ResponseResult;

@Component
public class AdminAuthClientFallback implements AdminAuthClient {
    @Override
    public ResponseResult<UserDTO> login(String phone, String password) {
        return ResponseResult.toError("未找到用户");
    }

    @Override
    public ResponseResult<List<String>> getRolesBySysUserId(Long sysUserId) {
        return ResponseResult.toError("未找到角色");
    }
}
