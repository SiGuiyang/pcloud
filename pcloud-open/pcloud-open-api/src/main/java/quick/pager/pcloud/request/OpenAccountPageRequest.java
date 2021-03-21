package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import quick.pager.pcloud.model.request.PageRequest;

/**
 * 开放账户
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAccountPageRequest extends PageRequest {
    private static final long serialVersionUID = 8685399397102380768L;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 状态 0 开启 1 禁用 2 冻结
     */
    private Integer status;
}
