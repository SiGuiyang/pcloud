package quick.pager.pcloud.request;

import java.time.LocalDateTime;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求基类
 *
 * @author siguiyang
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = -8303934871640269088L;

    /**
     * 主键
     */
    private Long id;
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

}
