package quick.pager.pcloud.log.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import quick.pager.pcloud.model.request.Request;

/**
 * 日志保存
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogSaveRequest extends Request {
    private static final long serialVersionUID = -9181373825316199258L;

    private String _id;
    /**
     * 访问人
     */
    private String name;
    /**
     * 访问人主键
     */
    private String userId;
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
}
