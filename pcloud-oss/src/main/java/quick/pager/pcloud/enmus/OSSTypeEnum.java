package quick.pager.pcloud.enmus;


import quick.pager.pcloud.enums.IEnum;

/**
 * OOS 云类型枚举
 *
 * @author siguiyang
 */
public enum OSSTypeEnum implements IEnum<String> {

    ALIYUN("ALIYUN", "阿里云"),
    TENCENT("TENCENT", "腾讯云"),
    QINIU("QINIU", "七牛云");

    private String code;

    private String desc;

    OSSTypeEnum(String code, String desc) {
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
