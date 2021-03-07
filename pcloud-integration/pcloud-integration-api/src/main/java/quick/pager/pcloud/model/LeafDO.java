package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_leaf_alloc")
public class LeafDO implements Serializable {

    /**
     * 业务号段类型
     */
    private String bizTag;
    /**
     * 最大值
     */
    private Long maxId;
    /**
     * 步长
     */
    private Integer step;
    /**
     * 业务号段说明
     */
    private String description;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 所属负责人
     */
    private String author;
}
