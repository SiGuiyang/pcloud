package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单响应对象
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = -6427332873494498814L;

    /**
     * 父级主键
     */
    private Long parentId;
    /**
     * 序号
     */
    private Integer sequence;
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

    private Long id;

    private String label;

    private String gmtModifiedName;

    private LocalDateTime gmtModifiedDate;

    private List<MenuDTO> children;

    private MenuDTO.Meta meta;

    @Data
    public static class Meta implements Serializable {

        private static final long serialVersionUID = -3075193428932941702L;

        private String title;

        private String icon;

        private boolean noCache;

        private List<String> permission;

        public Meta(String title, String icon, boolean noCache, List<String> permission) {
            this.title = title;
            this.icon = icon;
            this.noCache = noCache;
            this.permission = permission;
        }
    }
}
