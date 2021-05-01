package quick.pager.pcloud.service;

import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.open.request.OpenLoginRequest;

/**
 * 登录服务
 *
 * @author siguiyang
 */
public interface OpenLoginService {

    /**
     * 登录
     *
     * @param request 请求参数
     * @return 登录信息
     */
    ResponseResult<OpenAccountDTO> login(final OpenLoginRequest request);
}
