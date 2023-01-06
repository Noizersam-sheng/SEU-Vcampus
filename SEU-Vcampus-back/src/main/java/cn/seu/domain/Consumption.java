package cn.seu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Consumption {
    /*
    id标识
     */
    private long longId;

    /*
    一卡通号
     */
    private Integer id;

    /*
    消费者名字
     */
    private String name;

    /*
    商品id
     */
    private String commodityId;

    /*
    商品名字
     */
    private String commodityName;

    /*
    商品类型
     */
    private String type;

    /*
    消费数量
     */
    private Integer num;

    /*
    商品总消费
     */
    private Double cost;

    /*
    消费时间
    */
    private Date time;
    /*
    二手还是二手
     */
    private Integer market;

}
