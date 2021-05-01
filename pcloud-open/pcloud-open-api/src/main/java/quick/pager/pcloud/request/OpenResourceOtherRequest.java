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
public class OpenResourceOtherRequest extends PageRequest {

    private static final long serialVersionUID = 4035326785991811219L;
    /**
     * 主键集
     */
    private List<Long> ids;
    /**
     * 角色名称
     */
    private String name;

}
