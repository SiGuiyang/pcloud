package quick.pager.pcloud.model.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserSaveRequest extends Request {
    private static final long serialVersionUID = 2802879969759895845L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 0 ：正常 1：禁用
     */
    private Boolean state;

    private List<Long> roleIds;

    private String avatar;

}
