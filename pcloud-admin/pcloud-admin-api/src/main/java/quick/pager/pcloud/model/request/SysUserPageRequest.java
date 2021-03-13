package quick.pager.pcloud.model.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserPageRequest extends PageRequest {
    private static final long serialVersionUID = 2802879969759895845L;

    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 登录账号
     */
    private String phone;
    /**
     * 昵称
     */
    private String username;

    private List<Long> roleIds;
}
