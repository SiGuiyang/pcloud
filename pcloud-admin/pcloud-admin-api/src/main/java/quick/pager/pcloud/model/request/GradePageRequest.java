package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 职级
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class GradePageRequest extends PageRequest {
    private static final long serialVersionUID = -8949585085105743448L;
    /**
     * 名称
     */
    private String name;
}
