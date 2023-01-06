package cn.seu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayInfoOutDTO implements Serializable {
    private long longId;

    private String outTradeNo;
}
