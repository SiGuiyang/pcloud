package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;

/**
 * 消息模板类型
 *
 * @author siguiyang
 */
@AllArgsConstructor
public enum MsgTemplateTypeEnums implements IEnum<Integer> {
    SMS(1, "短信"),
    PUSH(2, "push推送"),
    STATION(3, "站内信");

    private final Integer code;

    private final String desc;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
