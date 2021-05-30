package quick.pager.pcloud.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 需求管理
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class DemandAdminSaveRequest extends Request {
    private static final long serialVersionUID = -6414078278667858012L;

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
}
