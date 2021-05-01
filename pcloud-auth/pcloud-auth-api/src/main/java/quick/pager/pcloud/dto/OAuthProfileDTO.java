package quick.pager.pcloud.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthProfileDTO implements Serializable {

    private static final long serialVersionUID = 5462640627007106737L;
    private Long id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 登陆用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;
}
