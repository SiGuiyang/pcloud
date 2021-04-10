package quick.pager.pcloud.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * 请求日志
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDO implements Serializable {
    private static final long serialVersionUID = 7024405902397907197L;

    @MongoId
    private String _id;

    /**
     * 访问人主键
     */
    private String userId;
    /**
     * 访问人
     */
    @Indexed
    private String name;
    /**
     * 接口访问地址
     */
    private String path;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 请求ip地址
     */
    private String clientIp;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;

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
    @Indexed
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
    private Boolean deleteStatus;
}
