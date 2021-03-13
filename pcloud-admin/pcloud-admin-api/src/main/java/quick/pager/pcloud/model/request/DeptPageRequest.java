package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class DeptPageRequest extends PageRequest {
    private static final long serialVersionUID = -3757313168771831812L;
    /**
     * 名称
     */
    private String name;
}
