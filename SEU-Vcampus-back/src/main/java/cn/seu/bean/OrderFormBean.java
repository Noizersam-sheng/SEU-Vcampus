package cn.seu.bean;

import lombok.Data;

@Data
public class OrderFormBean {

    /*
    id标识
     */
    private long id;

    /*
    一卡通号
     */
    private int creditCard;

    /*
    商户订单号
     */
    private String outTradeNo;

    /*
    交易状态
     */
    private String tradeStatus;
}
