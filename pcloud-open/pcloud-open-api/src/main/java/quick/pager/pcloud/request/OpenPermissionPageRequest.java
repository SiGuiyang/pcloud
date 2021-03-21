package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import quick.pager.pcloud.model.request.PageRequest;

/**
 * 权限
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OpenPermissionPageRequest extends PageRequest {
    private static final long serialVersionUID = 1633274945908331123L;

    /**
     * 应用主键
     */
    private Long accountId;
    /**
     * 资源名称
     */
    private String name;
}
