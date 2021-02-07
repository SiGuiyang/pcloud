package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源
 *
 * @author siguiyang
 * @version 3.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceDTO implements Serializable {

    private static final long serialVersionUID = -8053449571621718554L;

    private Long id;
    /**
     * 父级主键
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 名称
     */
    private String label;
    /**
     * 访问路径
     */
    private String resourceUrl;
    /**
     * 资源类型
     */
    private Integer bizType;
    /**
     * 资源类型说明
     */
    private String bizTypeName;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;

    /**
     * 拥有子节点
     */
    private Boolean hasChildren;
    /**
     * 节点
     */
    private List<ResourceDTO> children;
}
