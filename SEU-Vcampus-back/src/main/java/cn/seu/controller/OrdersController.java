package cn.seu.controller;

import cn.seu.Impl.AliPayImpl;
import cn.seu.bean.AliPayBean;
import cn.seu.bean.OrderFormBean;
import cn.seu.config.AlipayConfig;
import cn.seu.domain.OrderForm;
import cn.seu.utils.UUIdUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;

@Controller
public class OrdersController {
    @Autowired
    private AliPayImpl aliPayimpl;

    @ResponseBody
    @RequestMapping("/goAlipay")
    // 传参方法 http://localhost:8088/goAlipay?id=111&outTradeNo=123
    public String goAlipay(@RequestParam("id") long id, @RequestParam("outTradeNo") String outTradeNo) throws Exception {
//		orders = ordersService.findById(orders.getId());
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
//		String out_trade_no = orders.getOrderNum();
//        String out_trade_no = UUIdUtils.next();
        OrderFormBean orderFormBean = new OrderFormBean();
        orderFormBean.setId(id);
        orderFormBean.setOutTradeNo(outTradeNo);
        OrderForm orderForm = aliPayimpl.searchFormByIdAndOutTradeNo(orderFormBean);
        System.out.println(orderForm);
        //付款金额，必填
        //订单名称，必填

        //商品描述，可空
        //String body = ordersDesc == null ? "" : ordersDesc;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "15m";
        AliPayBean aliPayBean = new AliPayBean();
        aliPayBean.setOut_trade_no(orderForm.getOutTradeNo());
        DecimalFormat df = new DecimalFormat("#.00"); // 保留两位小数
        aliPayBean.setTotal_amount(df.format(orderForm.getTotalAmount()));
        aliPayBean.setSubject(orderForm.getSubject());
        aliPayBean.setTimeout_express(timeout_express);
        alipayRequest.setBizContent(JSON.toJSONString(aliPayBean));
        System.out.println(aliPayBean);
//        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
//                + "\"total_amount\":\"" + total_amount + "\","
//                + "\"subject\":\"" + subject + "\","
//                //+ "\"body\":\""+ body +"\","
//                + "\"timeout_express\":\"" + timeout_express + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//		System.out.println(JSON.toJSONString(alipayRequest));
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }
}
