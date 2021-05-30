package quick.pager.pcloud.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理平台广告VO
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdAdminVO implements Serializable {
    private static final long serialVersionUID = -6598819717704531361L;

    private Long id;

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
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}
