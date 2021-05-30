package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ClassificationAdminSaveRequest extends Request {
    private static final long serialVersionUID = 135948996577304588L;

    /**
     * 分类名称
     */
    private String name;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 分类状态
     *
     * @see quick.pager.pcloud.enums.StateEnums
     */
    private Integer state;
}
