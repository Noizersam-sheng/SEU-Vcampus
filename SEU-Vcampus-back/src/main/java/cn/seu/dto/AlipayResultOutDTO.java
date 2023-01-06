package cn.seu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayResultOutDTO implements Serializable {
    /*
    status:
    - TRADE_FINISHED
    - TRADE_SUCCESS
    - TRADE_UNPAY
    - TRADE_EXPIRE
     */
    private String outTradeNo;
    private String status;

}
