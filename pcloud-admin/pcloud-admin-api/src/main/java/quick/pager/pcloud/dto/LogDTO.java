package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求日志
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDTO implements Serializable {

    private static final long serialVersionUID = -5476468641692523207L;
    private String _id;

    /**
     * 访问人主键
     */
    private String userId;
    /**
     * 访问人
     */
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
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}
