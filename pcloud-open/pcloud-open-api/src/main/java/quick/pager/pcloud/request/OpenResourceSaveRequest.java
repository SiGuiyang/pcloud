package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import quick.pager.pcloud.model.request.Request;

/**
 * 角色保存Param
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpenResourceSaveRequest extends Request {

    private static final long serialVersionUID = -4753419002565550024L;
    /**
     * 名称
     */
    private String name;
    /**
     * 接口资源
     */
    private String resourceUrl;
}
