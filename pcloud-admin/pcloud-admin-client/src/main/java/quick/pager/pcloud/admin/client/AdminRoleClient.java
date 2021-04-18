package quick.pager.pcloud.admin.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import quick.pager.pcloud.admin.fallback.AdminRoleClientFallback;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 全角色权限
 *
 * @author siguiyang
 */
@FeignClient(value = "pcloud-admin-api", path = "/admin", fallbackFactory = AdminRoleClientFallback.class)
public interface AdminRoleClient {

    @GetMapping("/role/permission")
    ResponseResult<Map<String, List<String>>> permission();
}
