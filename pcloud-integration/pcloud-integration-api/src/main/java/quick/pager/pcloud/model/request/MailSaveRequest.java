package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 站内信保存
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MailSaveRequest extends Request {

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 收信人手机号码
     */
    private String phone;
    /**
     * 短信内容
     */
    private String description;
}
