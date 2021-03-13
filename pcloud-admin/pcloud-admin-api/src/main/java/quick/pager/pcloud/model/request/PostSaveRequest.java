package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位saveRequest
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostSaveRequest extends Request {

    private static final long serialVersionUID = 1106889390800905462L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 父级主键
     */
    private Long parentId;

    /**
     * 职级名称
     */
    private String name;
}
