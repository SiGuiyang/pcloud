package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;

/**
 * 广告位
 *
 * @author siguiyang
 */
@AllArgsConstructor
public enum AdTypeEnums implements IEnum<Integer> {

    BANNER(0, "banner位"),
    HOME_POP(1, "首页弹框位"),
    SPLASH_SCREEN(2, "闪屏位"),
    FLOATING(3, "悬浮位");

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
