package quick.pager.pcloud.model.request;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 岗位其他request
 *
 * @author siguiyang
 */
@Data
public class PostOtherRequest implements Serializable {
    private static final long serialVersionUID = -3880972517526890997L;

    /**
     * 主键集
     */
    private List<Long> ids;
    /**
     * 父级主键
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
}
