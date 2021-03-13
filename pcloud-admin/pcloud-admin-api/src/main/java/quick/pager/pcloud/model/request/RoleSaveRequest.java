package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色保存Param
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleSaveRequest extends Request {
    private static final long serialVersionUID = 7463357139222297502L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String roleCode;
}
