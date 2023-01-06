package cn.seu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Record {
    /*
    id标识
     */
    private long longId;

    /*
    一卡通号
     */
    private int id;

    /*
    消费类型
     */
    private String ways;

    /*
    消费金额
     */
    private Double cost;

    /*
    一卡通余额
     */
    private Double surplus;

    /*
    消费时间
     */
    private Date time;
}
