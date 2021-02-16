package quick.pager.pcloud.integration.request;

import java.io.Serializable;
import lombok.Data;
import quick.pager.pcloud.integration.enums.SmsSourceEnums;

/**
 * 发送短信
 *
 * @author siguiyang
 */
@Data
public class SmsRequest implements Serializable {
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 事件源
     */
    private SmsSourceEnums smsSourceEnums;
}
