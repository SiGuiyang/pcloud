package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色保存Param
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceSaveRequest extends Request {
    private static final long serialVersionUID = 7463357139222297502L;

    /**
     * 父级主键
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 接口资源
     */
    private String resourceUrl;
    /**
     * 资源类型
     */
    private Integer bizType;
}
