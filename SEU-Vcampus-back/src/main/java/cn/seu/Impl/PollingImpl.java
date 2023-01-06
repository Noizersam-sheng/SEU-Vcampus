package cn.seu.Impl;

import cn.seu.api.Polling;
import cn.seu.domain.OrderForm;
import cn.seu.dto.AlipayResultInDTO;
import cn.seu.dto.AlipayResultOutDTO;
import cn.seu.mapper.OrderFormMapper;
import cn.seu.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;

public class PollingImpl implements Polling {

    @Override
    public AlipayResultOutDTO inquireTradeStatus(AlipayResultInDTO alipayResultInDTO) {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        OrderFormMapper orderFormMapper = sqlSession.getMapper(OrderFormMapper.class);
        OrderForm orderForm = orderFormMapper.searchFormByOutTradeNo(alipayResultInDTO.getOutTradeNo());
        AlipayResultOutDTO alipayResultOutDTO = new AlipayResultOutDTO();
        alipayResultOutDTO.setOutTradeNo(orderForm.getOutTradeNo());
        alipayResultOutDTO.setStatus(orderForm.getTradeStatus());
        return alipayResultOutDTO;
    }

}
