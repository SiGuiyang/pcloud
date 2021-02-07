package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("t_dept")
public class DeptDO implements Serializable {
    private static final long serialVersionUID = 5195345983397277986L;

    /**
     * 数据库主键
     */
    @TableId(type = IdType.AUTO)
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
     * 部门名称
     */
    private String name;
    /**
     * 成立时间
     */
    private LocalDateTime establishedTime;
    /**
     * 创建人
     */
    private String gmtCreatedName;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录创建时间
     */
    private LocalDateTime gmtCreatedDate;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
    /**
     * 数据库删除标志<br />
     * 1: 删除
     * 0: 未删除
     */
    @TableLogic(value = "false", delval = "true")
    private Boolean deleteStatus;
}
