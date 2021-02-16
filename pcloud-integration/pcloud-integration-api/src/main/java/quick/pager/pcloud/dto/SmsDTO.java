package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信数据
 *
 * @author siguiyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsDTO implements Serializable {

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 短信编码
     */
    private String code;
    /**
     * 短信内容
     */
    private String description;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}
