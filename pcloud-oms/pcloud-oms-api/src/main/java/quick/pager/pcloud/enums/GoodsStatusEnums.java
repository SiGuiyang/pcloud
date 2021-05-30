package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;

/**
 * 商品状态
 *
 * @author siguiyang
 */
@AllArgsConstructor
public enum GoodsStatusEnums implements IEnum<Integer> {

    APPLY(0, "待审核"),
    REJECT(1, "审核拒绝"),
    PASS(2, "审核通过");

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
