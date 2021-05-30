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
 * 消息模板
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("msg_template")
public class MsgTemplateDO implements Serializable {
    private static final long serialVersionUID = 3760580917803989077L;


    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板编号
     */
    private String code;
    /**
     * 模板类型 1: 短信 2: push 3: 站内信
     */
    private Integer type;
    /**
     * 模板内容
     */
    private String content;
    /**
     * 状态
     *
     * @see quick.pager.pcloud.enums.StateEnums
     */
    private Integer state;
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
