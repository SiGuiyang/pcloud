package quick.pager.pcloud.request;

import lombok.Data;

/**
 * 站内信分页
 *
 * @author siguiyang
 */
@Data
public class MailPageRequest extends PageRequest {

    /**
     * 名称
     */
    private String name;
    /**
     * 收信人手机号码
     */
    private String phone;
    /**
     * 站内信状态
     * false : 未读
     * true : 已读
     */
    private Boolean state;
}
