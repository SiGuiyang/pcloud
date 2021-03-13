package quick.pager.pcloud.service;

import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 登录服务
 *
 * @author siguiyang
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param phone    手机号码
     * @param password 密码
     */
    ResponseResult<SysUserDTO> login(final String phone, final String password);
}
