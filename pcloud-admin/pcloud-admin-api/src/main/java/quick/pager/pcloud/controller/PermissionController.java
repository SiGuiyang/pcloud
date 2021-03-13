package quick.pager.pcloud.controller;

import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.model.request.AuthorizationRequest;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.PermissionService;
import quick.pager.pcloud.utils.Assert;

/**
 * 权限
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/admin")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 菜单授权
     *
     * @param request 请求参数
     */
    @PostMapping("/permission/grant")
    public ResponseResult grant(@RequestBody AuthorizationRequest request) {

        Assert.isTrue(Objects.nonNull(request.getRoleId()), () -> ResponseStatus.PARAMS_EXCEPTION);

        return permissionService.grant(request);
    }
}
