package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信保存
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SmsSaveRequest extends Request {

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
}
