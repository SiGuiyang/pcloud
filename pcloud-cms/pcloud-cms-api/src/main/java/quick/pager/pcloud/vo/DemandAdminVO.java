package quick.pager.pcloud.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 需求管理
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandAdminVO implements Serializable {
    private static final long serialVersionUID = -3110152350838143056L;

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
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}
