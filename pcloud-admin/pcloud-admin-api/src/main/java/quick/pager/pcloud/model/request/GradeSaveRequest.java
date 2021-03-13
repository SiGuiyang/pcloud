package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 职级saveRequest
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GradeSaveRequest extends Request {
    private static final long serialVersionUID = 4702006982036709653L;

    private Long id;

    /**
     * 职级名称
     */
    private String name;
}
