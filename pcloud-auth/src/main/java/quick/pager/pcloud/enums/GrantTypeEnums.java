package quick.pager.pcloud.enums;

/**
 * 授权方式
 *
 * @author siguiyang
 */
public enum GrantTypeEnums implements IEnum<String> {

    ADMIN("admin", "管理平台"),
    OPEN("open", "开放平台"),
    APP("app", "APP移动端"),
    H5("h5", "H5"),
    WECHAT("wechat", "微信"),
    ALIPAY("alipay", "支付宝"),
    ONE_LOGIN("one_login", "一键登录");


    public final String code;

    private final String desc;

    GrantTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
