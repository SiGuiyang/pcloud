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
public class ResourceTreeDTO implements Serializable {

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
     * 资源类型说明
     */
    private String bizTypeName;

    /**
     * 拥有子节点
     */
    private Boolean hasChildren;
    /**
     * 节点
     */
    private List<ResourceTreeDTO> children;
}
