package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageRequest extends PageRequest {
    private static final long serialVersionUID = 7463357139222297502L;

    /**
     * 角色名称
     */
    private String name;

}
