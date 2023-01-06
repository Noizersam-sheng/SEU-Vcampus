package cn.seu.domain.store;

import lombok.Data;

import java.util.Date;

@Data
public class Commodity {
    /*
    商品id
     */
    private String commodityId;

    /*
    商品类型
     */
    private String type;

    /*
    商品名称
     */
    private  String commodityName;

    /*
    商品价格
     */
    private Double price;

    /*
    商品价值
     */
    private Double value;

    /*
    商品折扣
     */
    private Double discount;

    /*
    商品入库时间
     */
    private Date time;

    /*
    商品现存数量
     */
    private Integer number;

    /*
    一手还是二手
     */
    private String hand;

    /*
    商品所有者
     */
    private String poster;

    /*
    商品图片路径
     */
    private String image;
}
