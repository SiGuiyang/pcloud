package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;
import quick.pager.pcloud.exception.PCloudException;

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

    public static StateEnums parse(final Integer code) {
        for (StateEnums stateEnums : StateEnums.values()) {
            if (stateEnums.code.equals(code)) {
                return stateEnums;
            }
        }
        throw new PCloudException("未找到相对应的枚举类型");
    }
}
