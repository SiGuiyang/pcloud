package quick.pager.pcloud.enums;

/**
 * 开放账户状态
 *
 * @author siguiyang
 */
public enum OpenAccountStatusEnums implements IEnum<Integer> {

    NORMAL(0, "正常"),
    FORBIDDEN(1, "禁用"),
    FROZEN(2, "冻结");

    private final Integer code;

    private final String desc;

    OpenAccountStatusEnums(Integer code, String desc) {
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
}
