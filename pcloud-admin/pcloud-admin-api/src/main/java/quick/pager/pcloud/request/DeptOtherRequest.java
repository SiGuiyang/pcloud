package quick.pager.pcloud.request;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 部门其他request
 *
 * @author siguiyang
 */
@Data
public class DeptOtherRequest implements Serializable {
    private static final long serialVersionUID = -6578405509930006524L;
    /**
     * 主键集
     */
    private List<Long> ids;
    /**
     * 名称
     */
    private String name;
}
