package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import quick.pager.pcloud.model.request.Request;

/**
 * 开放账号
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAccountSaveRequest extends Request {
    private static final long serialVersionUID = -8076907534307047340L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 状态 0 开启 1 禁用 2 冻结
     */
    private Integer status;

    /**
     * secureId
     */
    private String secureId;

    /**
     * secureKey
     */
    private String secureKey;
}
