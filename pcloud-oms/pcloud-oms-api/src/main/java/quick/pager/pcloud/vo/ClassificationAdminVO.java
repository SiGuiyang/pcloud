package quick.pager.pcloud.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationAdminVO implements Serializable {
    private static final long serialVersionUID = -5072821294999685368L;

    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 分类状态
     *
     * @see quick.pager.pcloud.enums.StateEnums
     */
    private Integer state;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}
