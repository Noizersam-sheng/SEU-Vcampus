package cn.seu.test;

import cn.seu.Impl.AliPayImpl;
import cn.seu.bean.AliPayBean;
import cn.seu.api.AliPay;

import com.alipay.api.AlipayApiException;
import org.junit.Test;

public class AlipayTest {

    @Test
    public void testPay() {
        AliPayBean aliPayBean = new AliPayBean();
        aliPayBean.setOut_trade_no("789");
        aliPayBean.setSubject("校园卡充值");
        aliPayBean.setTotal_amount("100");
//        System.out.println(aliPayBean);
        String result = null;
        AliPay aliPay = new AliPayImpl();
        try {
            result = aliPay.pay(aliPayBean);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
