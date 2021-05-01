package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;

/**
 * 状态
 *
 * @author siguiyang
 */
@AllArgsConstructor
public enum StateEnums implements IEnum<Integer> {

    OPEN(0, "开启"),
    CLOSE(1, "关闭");

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
