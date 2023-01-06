package cn.seu.domain;

import lombok.Data;

@Data
public class User {
    /*
    一卡通号
     */
    private Integer id;

    /*
    别名
     */
    private String nickname;

    /*
    密码
     */
    private String password;

    /*
    余额
     */
    private Double balance;

    /*
    照片路劲
     */
    private String url;

    /*
    网费
     */
    private Double netFee;
}
