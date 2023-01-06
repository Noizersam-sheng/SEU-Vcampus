package cn.seu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderForm {
    /*
    id标识
     */
    private long id;
    /*
    一卡通号
     */
    private Integer creditCard;
    /*
    商户订单号
     */
    private String outTradeNo;
    /*
    交易总金额
     */
    private Double totalAmount;
    /*
    appid
     */
    private String appId;
    /*
    下单来源
     */
    private String subject;
    /*
    交易状态
     */
    private String tradeStatus;
    /*
    商品id
     */
    private String commodityId;
    /*
    购买商品数量
     */
    private Integer num;
    /*
    交易创建时间
     */
    private Date gmtCreate;
    /*
    付款时间
     */
    private Date gmtPayment;
    /*
    支付宝交易号
     */
    private String tradeNo;
    /*
    买家支付宝账号
     */
    private String buyerId;
}
