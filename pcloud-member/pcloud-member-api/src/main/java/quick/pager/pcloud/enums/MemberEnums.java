package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberEnums implements ErrorCodeEnums {

    TEST_ERROR(2000, "测试错误枚举");

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
