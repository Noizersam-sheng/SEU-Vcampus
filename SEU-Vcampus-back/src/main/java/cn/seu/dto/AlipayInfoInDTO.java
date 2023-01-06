package cn.seu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AlipayInfoInDTO implements Serializable {
    /*
    一卡通号
     */
    private String cardId;

    private Double totalAmount;

    private String subject;

    private String commodityId;

    private Integer num;
}
