package cn.seu.api;

import cn.seu.dto.AlipayResultInDTO;
import cn.seu.dto.AlipayResultOutDTO;

public interface Polling {
    AlipayResultOutDTO inquireTradeStatus(AlipayResultInDTO alipayResultInDTO);
}
