package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PostPageRequest extends PageRequest {
    private static final long serialVersionUID = 2936631822224752791L;
    /**
     * 名称
     */
    private String name;
}
