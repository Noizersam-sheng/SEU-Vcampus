package cn.seu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliPayBean {
    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 付款金额
     */
    private String total_amount;

    /**
     * 订单名称
     */
    private String subject;

    /**
     * 商品描述
     */
    private String body=null;

    /**
     * 超时时间参数
     */
    private String timeout_express = "15m";

    /**
     * 产品编号
     */
    private String product_code = "FAST_INSTANT_TRADE_PAY";
}
