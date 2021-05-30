package quick.pager.pcloud.oms.enums;


import lombok.AllArgsConstructor;
import quick.pager.pcloud.enums.IEnum;

/**
 * 状态
 *
 * @author siguiyang
 */
@AllArgsConstructor
public enum StateEnums implements IEnum<Integer> {

    ON(0, "上架"),
    OFF(1, "下架");

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
