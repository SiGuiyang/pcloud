package quick.pager.pcloud.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 会员发送短信
 *
 * @author siguiyang
 */
@Data
public class MemberSmsRequest implements Serializable {

    private static final long serialVersionUID = -5412955039750982262L;
    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 事件源
     */
    private String source;
}
