package quick.pager.pcloud.model;

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
import quick.pager.pcloud.annotation.IdGen;

/**
 * 商品
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("oms_goods")
public class GoodsDO implements Serializable {
    private static final long serialVersionUID = 8572529788497610857L;

    @TableId
    @IdGen("oms_goods")
    private Long id;
    /**
     * 分类主键
     */
    private Long classificationIdRel;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 商品主图
     */
    private String masterPicUrl;
    /**
     * 商品详情图集
     */
    private String detailPicUrl;
    /**
     * 商品说明图
     */
    private String descriptionPicUrl;
    /**
     * 商品状态
     *
     * @see quick.pager.pcloud.enums.GoodsStatusEnums
     */
    private Integer goodsStatus;
    /**
     * 状态
     *
     * @see quick.pager.pcloud.oms.enums.StateEnums
     */
    private Integer state;
    /**
     * 商品价格
     */
    private BigDecimal amount;
    /**
     * 商品会员价格
     */
    private BigDecimal vipAmount;
    /**
     * 开始时间
     */
    private LocalDate startDate;
    /**
     * 结束时间/过期时间
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
