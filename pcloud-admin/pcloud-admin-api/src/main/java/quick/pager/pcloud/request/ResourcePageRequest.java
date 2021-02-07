package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourcePageRequest extends PageRequest {

    private static final long serialVersionUID = -3826378719809870040L;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级主键
     */
    private Long parentId;
}
