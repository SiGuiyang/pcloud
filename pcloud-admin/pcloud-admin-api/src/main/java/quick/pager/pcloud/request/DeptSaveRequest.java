package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门saveRequest
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeptSaveRequest extends Request {

    private static final long serialVersionUID = -4678523736284818692L;

    private Long id;
    private Long parentId;

    /**
     * 职级名称
     */
    private String name;
    /**
     * 部门经理主键
     */
    private Long managerId;
}
