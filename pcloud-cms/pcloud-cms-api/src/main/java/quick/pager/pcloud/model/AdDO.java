package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广告位
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("cms_ad")
public class AdDO implements Serializable {

    private static final long serialVersionUID = -670006012631665754L;

    @TableId(type = IdType.AUTO)
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
