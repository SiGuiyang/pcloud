package quick.pager.pcloud.request;

import lombok.Data;

/**
 * 短信分页
 *
 * @author siguiyang
 */
@Data
public class SmsPageRequest extends PageRequest {

    /**
     * 名称
     */
    private String name;
    /**
     * 短信编码
     */
    private String code;
}
