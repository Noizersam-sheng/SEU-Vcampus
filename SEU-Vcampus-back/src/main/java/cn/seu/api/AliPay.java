package cn.seu.api;

import cn.seu.bean.AliPayBean;
import cn.seu.bean.OrderFormBean;
import cn.seu.domain.OrderForm;
import cn.seu.domain.store.Commodity;
import cn.seu.dto.AlipayInfoInDTO;
import cn.seu.dto.AlipayInfoOutDTO;
import com.alipay.api.AlipayApiException;

public interface AliPay {
    String pay(AliPayBean aliPayBean) throws AlipayApiException;
    AlipayInfoOutDTO createOrderForm(AlipayInfoInDTO alipayInfoInDTO);
    OrderForm searchFormByIdAndOutTradeNo(OrderFormBean orderFormBean);
}
