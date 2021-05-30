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
public class DemandAdminPageRequest extends PageRequest {
    private static final long serialVersionUID = 8539983399371160979L;

    /**
     * 会员主键
     */
    private String phone;
    /**
     * 名称
     */
    private String name;
    /**
     * 编号
     */
    private String publishCode;
    /**
     * 状态
     *
     * @see quick.pager.pcloud.enums.PublishStatusEnums
     */
    private Integer status;
    /**
     * 生效开始时间
     */
    private LocalDate startDate;
    /**
     * 生效结束时间
     */
    private LocalDate endDate;
}
