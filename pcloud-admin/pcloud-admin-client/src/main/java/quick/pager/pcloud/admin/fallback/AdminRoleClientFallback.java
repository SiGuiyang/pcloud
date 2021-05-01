package quick.pager.pcloud.admin.fallback;

import feign.hystrix.FallbackFactory;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.admin.client.AdminRoleClient;
import quick.pager.pcloud.response.ResponseResult;

@Component
@Slf4j
public class AdminRoleClientFallback implements FallbackFactory<AdminRoleClient> {

    @Override
    public AdminRoleClient create(Throwable cause) {
        log.error("熔断信息 msg = {}", cause);
        return new AdminRoleClient() {
            @Override
            public ResponseResult<Map<String, List<String>>> permission() {
                return ResponseResult.toError("没有权限");
            }
        };
    }
}
