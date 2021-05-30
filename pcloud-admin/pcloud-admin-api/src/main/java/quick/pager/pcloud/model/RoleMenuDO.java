package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quick.pager.pcloud.annotation.IdGen;

/**
 * 角色权限
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_role_menu")
public class RoleMenuDO implements Serializable {
    private static final long serialVersionUID = 5296292799500727996L;

    /**
     * 数据库主键
     */
    @TableId
    @IdGen(value = "biz_role_menu")
    private Long id;
    /**
     * 角色主键
     */
    private Long roleId;
    /**
     * 菜单主键
     */
    private Long menuId;
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
