package quick.pager.pcloud.request;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class AdAdminSaveRequest extends Request {
    private static final long serialVersionUID = -3591910255178590516L;
    /**
     * 标题
     */
    private String title;
    /**
     * 广告图片
     */
    private String adUrl;

    /**
     * 点击跳转链接
     */
    private String clickUrl;
    /**
     * 状态
     *
     * @see quick.pager.pcloud.enums.StateEnums
     */
    private Integer state;
    /**
     * 广告类型
     *
     * @see quick.pager.pcloud.enums.AdTypeEnums
     */
    private Integer adType;
    /**
     * 开始时间
     */
    private LocalDate startDate;
    /**
     * 结束时间
     */
    private LocalDate endDate;
}
