package quick.pager.pcloud.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访问资源
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceOtherRequest extends PageRequest {

    private static final long serialVersionUID = 1547095062707284993L;

    /**
     * 主键集
     */
    private List<Long> ids;
    /**
     * 父级主键
     */
    private Long parentId;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 资源类型
     */
    private Integer bizType;

}
