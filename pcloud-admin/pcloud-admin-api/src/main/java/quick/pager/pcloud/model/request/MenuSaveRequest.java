package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuSaveRequest extends Request {

    private static final long serialVersionUID = 2086881622142051682L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 父级主键
     */
    private Long parentId;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 菜单类型
     */
    private Integer menuType;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 组件重定向地址
     */
    private String redirect;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 是否是路由
     */
    private Boolean router;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
}
