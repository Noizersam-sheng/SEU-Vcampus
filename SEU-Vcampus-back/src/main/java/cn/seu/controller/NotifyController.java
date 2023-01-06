package cn.seu.controller;

import cn.seu.Impl.AliPayImpl;
import cn.seu.Impl.WriteBackRecordImpl;
import cn.seu.bean.PersonBean;
import cn.seu.config.AlipayConfig;
import cn.seu.domain.Consumption;
import cn.seu.domain.OrderForm;
import cn.seu.domain.Record;
import cn.seu.domain.User;
import cn.seu.domain.store.Commodity;
import cn.seu.mapper.OrderFormMapper;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class NotifyController {

    @Autowired
    private AliPayImpl aliPayimpl;

    @Autowired
    private WriteBackRecordImpl writeBackRecordimpl;

    /**
     * 支付订单后异步回调方法
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/notify")
    @ResponseBody
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        try {
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            System.out.println("支付宝支付返回参数params-------->" + params);
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            System.out.println("signVerified----->" + signVerified);
            PrintWriter out = null;
            if (signVerified) {
                //商户订单号  orderNo
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //买家支付宝交易账号
                String buyer_id = new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"), "UTF-8");
                //交易总金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                //实收金额
                String receipt_amount = new String(request.getParameter("receipt_amount").getBytes("ISO-8859-1"), "UTF-8");
                //付款金额
                String buyer_pay_amount = new String(request.getParameter("buyer_pay_amount").getBytes("ISO-8859-1"), "UTF-8");
                //交易时间
                String gmt_create = new String(request.getParameter("gmt_create").getBytes("ISO-8859-1"), "UTF-8");
                //交易付款时间
                String gmt_payment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8");
                //获取appid
                String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

                // 查询该订单
                OrderForm orderForm = aliPayimpl.searchFormByOutTradeNo(out_trade_no);
                //根据订单查询该订单是否存在
//                if (memberOrder == null) {
//                    throw new AlipayApiException("out_trade_no错误");
//                }
                //判断支付金额是否正确
//                if (!total_amount.equals(orderForm.getTotalAmount().toString())) {
//                    throw new AlipayApiException("error total_amount");
//                }
                //判断appid是否一致
                if (!app_id.equals(AlipayConfig.app_id)) {
                    throw new AlipayApiException("app_id不一致");
                }
                System.out.println("trade_status------------>" + trade_status);
                System.out.println("付款金额为：" + total_amount);
                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知

                    orderForm.setTradeStatus(trade_status);
                    aliPayimpl.updateOrderForm(orderForm);


                    System.out.println("订单已经完成，无需进行其他操作，通知前端----------");
                } else if (trade_status.equals("TRADE_SUCCESS")) {//向支付宝订单记录表中添加数据
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                    orderForm.setTradeNo(trade_no);
                    orderForm.setBuyerId(buyer_id);
                    orderForm.setTotalAmount(Double.valueOf(total_amount));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    orderForm.setGmtCreate(simpleDateFormat.parse(gmt_create));
                    orderForm.setGmtPayment(simpleDateFormat.parse(gmt_payment));
                    orderForm.setTradeStatus(trade_status);
                    orderForm.setAppId(app_id);
                    aliPayimpl.updateOrderForm(orderForm);
                    if (orderForm.getSubject().equals("一卡通充值") || orderForm.getSubject().equals("缴网费")) {
                        //充值
                        User user = writeBackRecordimpl.searchUserById(orderForm.getCreditCard());

                        if (orderForm.getSubject().equals("一卡通充值")) {
                            PersonBean personBean = new PersonBean();
                            personBean.setId(user.getId());
                            personBean.setBalance(user.getBalance() + orderForm.getTotalAmount());
                            writeBackRecordimpl.updateBalanceById(personBean);
                            user = writeBackRecordimpl.searchUserById(orderForm.getCreditCard());
                        }
                        Record record = new Record();
                        record.setId(orderForm.getCreditCard());
                        record.setWays(orderForm.getSubject());
                        record.setCost(orderForm.getTotalAmount());
                        record.setSurplus(user.getBalance());
                        record.setTime(orderForm.getGmtPayment());
                        writeBackRecordimpl.insertRecord(record);
                    }
                    if (orderForm.getSubject().equals("商店消费")) {
                        Commodity commodity= writeBackRecordimpl.searchCommodityById(orderForm.getCommodityId());
                        Consumption consumption = new Consumption();
                        consumption.setId(orderForm.getCreditCard());
                        consumption.setCommodityId(orderForm.getCommodityId());
                        consumption.setCommodityName(commodity.getCommodityName());
                        consumption.setType(commodity.getType());
                        consumption.setNum(orderForm.getNum());
                        consumption.setCost(orderForm.getTotalAmount());
                        consumption.setTime(simpleDateFormat.parse(gmt_payment));
                        if(commodity.getHand().equals("二手")){
                            consumption.setMarket(2);
                        }
                        else{
                            consumption.setMarket(1);
                        }
                        writeBackRecordimpl.insertConsumption(consumption);

                    }

                    System.out.println("订单成功支付，下面进行修改数据库的操作和通知前端-------");
                }
                //response.getOutputStream().print("success");
                return "success";
                //
            } else {//验签失败
                // response.getOutputStream().print("failure");
                return "failure";
            }
        } catch (AlipayApiException e) {
            System.out.println("-----------错误信息为:"+e.getErrMsg());
            throw new RuntimeException("调用支付宝接口发生异常");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("URLDecoderf发生异常");
        } /*catch (IOException e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException("IO发生异常");
//        } */ catch (ParseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("时间转换发生异常");
        }
    }
}
