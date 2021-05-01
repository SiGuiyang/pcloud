package quick.pager.pcloud.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * id生成器号段
 *
 * @author siguiyang
 */
@Data
public class IdGenSaveRequest implements Serializable {

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
