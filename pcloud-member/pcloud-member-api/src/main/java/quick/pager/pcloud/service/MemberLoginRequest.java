package quick.pager.pcloud.service;

import java.io.Serializable;
import lombok.Data;

/**
 * 登录请求参数
 *
 * @author siguiyang
 */
@Data
public class MemberLoginRequest implements Serializable {
    private static final long serialVersionUID = 8782225860806147349L;

    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 短信验证码
     */
    private String verifyCode;
    /**
     * 授权方式
     */
    private String grantType;

}
