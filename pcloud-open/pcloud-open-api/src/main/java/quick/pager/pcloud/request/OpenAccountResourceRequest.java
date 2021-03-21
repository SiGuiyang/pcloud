package quick.pager.pcloud.request;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 开放账户资源权限
 *
 * @author siguiyang
 */
@Data
public class OpenAccountResourceRequest implements Serializable {
    private static final long serialVersionUID = 8476251619620838439L;

    /**
     * 接口访问资源主键集
     */
    private List<Long> resourceIds;
    /**
     * 开放账户主键
     */
    private Long accountId;
}
