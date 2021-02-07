package quick.pager.pcloud.enums;

import quick.pager.pcloud.exception.PCloudException;

/**
 * 接口资源类型枚举
 *
 * @author siguiyang
 */
public enum ResourceBizTypeEnums implements IEnum<Integer> {

    INTERFACE(0, "接口"),

    BUTTON(1, "按钮");

    private final Integer code;

    private final String desc;


    ResourceBizTypeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }


    public static ResourceBizTypeEnums parse(int code) {
        for (ResourceBizTypeEnums bizTypeEnum : ResourceBizTypeEnums.values()) {
            if (bizTypeEnum.code == code) {
                return bizTypeEnum;
            }
        }
        throw new PCloudException("未找到正确的枚举值");
    }
}
