package quick.pager.pcloud.model.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserOtherRequest extends Request {

    private static final long serialVersionUID = 4605018155519897838L;

    /**
     * 用户主键集
     */
    private List<Long> ids;

    private String phone;

    private String username;

    private List<Long> roleIds;

}
