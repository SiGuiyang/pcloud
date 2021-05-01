package quick.pager.pcloud.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权参数
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationRequest extends Request {

    private static final long serialVersionUID = 4399880937415097395L;
    /**
     * 权限
     */
    private List<Long> routers;
    /**
     * 访问资源
     */
    private List<Long> resources;
    /**
     * 角色主键
     */
    private Long roleId;
}
