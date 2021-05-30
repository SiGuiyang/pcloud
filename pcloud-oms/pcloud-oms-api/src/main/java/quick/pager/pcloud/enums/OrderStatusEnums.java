package quick.pager.pcloud.enums;

import lombok.AllArgsConstructor;

/**
 * 订单状态
 *
 * @author siguiyang
 */
@AllArgsConstructor
public enum OrderStatusEnums implements IEnum<Integer> {

    BE_PAID(0, "待付款"),
    BE_RECEIVED(0, "待收货"),
    BE_EVALUATED(0, "待评价"),
    EVALUATED(0, "已评价"),
    SIGNED(0, "已签收"),
    COMPLETE(0, "订单完成"),
    REFUND_APPLICATION(0, "退款申请"),
    RETURNED(0, "已退款"),
    CLOSE(0, "订单关闭"),
    CANCEL(0, "订单取消");

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
