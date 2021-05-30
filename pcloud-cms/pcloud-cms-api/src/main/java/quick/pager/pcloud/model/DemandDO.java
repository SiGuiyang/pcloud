package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内容发布
 * 类似于商品
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("cms_demand")
public class DemandDO implements Serializable {
    private static final long serialVersionUID = -1475355985511112263L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 会员主键
     */
    private Long memberIdRel;
    /**
     * 名称
     */
    private String name;
    /**
     * 编号
     */
    private String publishCode;
    /**
     * 发布内容
     */
    private String content;
    /**
     * 跳转地址
     */
    private String jumpUrl;
    /**
     * 状态
     *
     * @see quick.pager.pcloud.enums.PublishStatusEnums
     */
    private Integer status;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 生效开始时间
     */
    private LocalDate startDate;
    /**
     * 生效结束时间
     */
    private LocalDate endDate;
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
