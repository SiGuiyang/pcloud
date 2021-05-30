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
 * 菜单资源
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_menu")
public class MenuDO implements Serializable {

    private static final long serialVersionUID = -3246720425417855255L;
    /**
     * 数据库主键
     */
    @TableId
    @IdGen(value = "biz_menu")
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
