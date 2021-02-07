package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职级
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDTO implements Serializable {

    private static final long serialVersionUID = 2801778912519227780L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 职级名称
     */
    private String name;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}
