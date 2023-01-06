package cn.seu.Impl;

import cn.seu.api.AliPay;
import cn.seu.bean.AliPayBean;
import cn.seu.bean.OrderFormBean;
import cn.seu.config.AlipayConfig;
import cn.seu.domain.OrderForm;
import cn.seu.domain.store.Commodity;
import cn.seu.dto.AlipayInfoInDTO;
import cn.seu.dto.AlipayInfoOutDTO;
import cn.seu.mapper.CommodityMapper;
import cn.seu.mapper.OrderFormMapper;
import cn.seu.utils.SqlSessionUtils;
import cn.seu.utils.UUIdUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AliPayImpl implements AliPay {
    private final String format = "json";

    @Autowired
    private OrderFormMapper orderFormMapper;

    @Override
    public String pay(AliPayBean aliPayBean) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,
                format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        alipayRequest.setBizContent(JSON.toJSONString(aliPayBean));
        System.out.println("封装请求支付宝付款参数为:{}" + JSON.toJSONString(alipayRequest));

        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println("请求支付宝付款返回参数为:{}" + result);

        return result;
    }

    public AlipayInfoOutDTO createOrderForm(AlipayInfoInDTO alipayInfoInDTO) {
        System.out.println(alipayInfoInDTO.toString());
        String out_trade_no = UUIdUtils.next();

        OrderForm orderForm = new OrderForm();
        Integer cardId = Integer.parseInt(alipayInfoInDTO.getCardId());
        orderForm.setCreditCard(cardId);
        orderForm.setOutTradeNo(out_trade_no);
        orderForm.setTotalAmount(alipayInfoInDTO.getTotalAmount());
        orderForm.setAppId(AlipayConfig.app_id);
        orderForm.setSubject(alipayInfoInDTO.getSubject());
        orderForm.setTradeStatus("TRADE_UNPAY");
        orderForm.setCommodityId(alipayInfoInDTO.getCommodityId());
        orderForm.setNum(alipayInfoInDTO.getNum());

        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        orderFormMapper= sqlSession.getMapper(OrderFormMapper.class);
        orderFormMapper.addOrderForm(orderForm);

        AlipayInfoOutDTO alipayInfoOutDTO = new AlipayInfoOutDTO();
        OrderForm orderFormResult = orderFormMapper.searchFormByOutTradeNo(out_trade_no);
        System.out.println(orderFormResult.toString());
        alipayInfoOutDTO.setLongId(orderFormResult.getId());
        alipayInfoOutDTO.setOutTradeNo(orderFormResult.getOutTradeNo());
        sqlSession.close();
        return alipayInfoOutDTO;
    }

    public OrderForm searchFormByIdAndOutTradeNo(OrderFormBean orderFormBean) {
        return orderFormMapper.searchFormByIdAndTradeNo(orderFormBean);
    }

    public OrderForm searchFormByOutTradeNo(String outTradeNo) {
        return orderFormMapper.searchFormByOutTradeNo(outTradeNo);
    }

    public int updateOrderForm(OrderForm orderForm) {
        return orderFormMapper.updateOrderForm(orderForm);
    }

}

