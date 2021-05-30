package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 消息模板
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MsgTemplateSaveRequest extends Request {
    private static final long serialVersionUID = -7731574959173082040L;

    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板编号
     */
    private String code;
    /**
     * 模板类型 1: 短信 2: push 3: 站内信
     */
    private Integer type;
    /**
     * 模板内容
     */
    private String content;
    /**
     * 状态
     *
     * @see quick.pager.pcloud.enums.StateEnums
     */
    private Integer state;
}
