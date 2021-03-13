package quick.pager.pcloud.admin.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 5861943882572628472L;
    private Long id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 登陆用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 角色编码
     */
    private List<String> authorities;
}
