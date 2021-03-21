package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源
 *
 * @author siguiyang
 * @version 3.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenResourceDTO implements Serializable {

    private static final long serialVersionUID = -8053449571621718554L;

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 访问路径
     */
    private String resourceUrl;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;

    /**
     * 拥有子节点
     */
    private Boolean hasChildren;
}
