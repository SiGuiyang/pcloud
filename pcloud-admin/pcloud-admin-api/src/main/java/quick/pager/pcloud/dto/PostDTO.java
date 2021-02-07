package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 岗位
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO implements Serializable {
    private static final long serialVersionUID = -3465085884578930024L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 上级岗位主键
     */
    private Long parentId;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 上级岗位名称
     */
    private String parentName;
    /**
     * 岗位名称
     */
    private String label;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
    /**
     * 子节点
     */
    private List<PostDTO> children;
}
