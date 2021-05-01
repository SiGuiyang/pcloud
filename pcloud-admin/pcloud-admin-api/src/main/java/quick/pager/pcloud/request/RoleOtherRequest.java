package quick.pager.pcloud.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleOtherRequest extends PageRequest {
    private static final long serialVersionUID = 7463357139222297502L;

    /**
     * 主键集
     */
    private List<Long> ids;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String roleCode;

}
