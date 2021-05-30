package quick.pager.pcloud.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理平台广告管理
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class AdAdminPageRequest extends PageRequest {
    private static final long serialVersionUID = -3234498152932706125L;

    /**
     * 标题
     */
    private String title;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
}
