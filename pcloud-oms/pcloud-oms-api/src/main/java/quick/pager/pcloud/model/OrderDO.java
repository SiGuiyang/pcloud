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
 * 订单 主表
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("oms_order")
public class OrderDO implements Serializable {

    private static final long serialVersionUID = 7382336261664104134L;
    /**
     * 主键
     */
    @TableId
    @IdGen("oms_order")
    private Long id;
    /**
     * 会员主键
     */
    private Long memberIdRel;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 订单状态
     *
     * @see quick.pager.pcloud.enums.OrderStatusEnums
     */
    private Integer orderStatus;
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
