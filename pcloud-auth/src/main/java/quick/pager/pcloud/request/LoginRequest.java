package quick.pager.pcloud.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 登录请求
 *
 * @author siguiyang
 */
@Data
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = -4121223579868535070L;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 授权方式
     */
    private String grantType;
    /**
     * 短信验证码
     */
    private String verifyCode;
}
