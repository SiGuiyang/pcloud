package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ClassificationAdminPageRequest extends PageRequest {
    private static final long serialVersionUID = -4800172472533985164L;

    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类状态
     *
     * @see quick.pager.pcloud.enums.StateEnums
     */
    private Integer state;
}
