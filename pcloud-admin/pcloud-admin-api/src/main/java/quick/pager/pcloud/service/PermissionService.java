package quick.pager.pcloud.service;

import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.AuthorizationRequest;

/**
 * 权限服务
 *
 * @author siguiyang
 * @version 3.0
 */
public interface PermissionService {

    /**
     * 授权
     *
     * @param request 请求参数
     * @return 返回授权成功的提示信息
     */
    ResponseResult grant(final AuthorizationRequest request);
}
