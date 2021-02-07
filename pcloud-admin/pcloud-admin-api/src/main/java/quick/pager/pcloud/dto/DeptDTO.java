package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptDTO implements Serializable {

    private static final long serialVersionUID = -4660253489132506580L;

    private Long id;
    /**
     * 上级部门主键
     */
    private Long parentId;
    /**
     * 部门经理主键
     */
    private Long managerId;
    /**
     * 上级部门
     */
    private String parentName;
    /**
     * 部门经理
     */
    private String managerName;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门名称
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
     * 成立时间
     */
    private LocalDateTime establishedTime;
    /**
     * 子节点
     */
    private List<DeptDTO> children;
}
