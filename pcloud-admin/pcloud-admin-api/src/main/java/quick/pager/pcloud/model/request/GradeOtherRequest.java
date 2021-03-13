package quick.pager.pcloud.model.request;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 职级其他request
 *
 * @author siguiyang
 */
@Data
public class GradeOtherRequest implements Serializable {
    private static final long serialVersionUID = 4916943003390521820L;
    /**
     * 主键集
     */
    private List<Long> ids;
    /**
     * 名称
     */
    private String name;
}
