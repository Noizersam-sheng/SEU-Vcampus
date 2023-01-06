package cn.seu.ui.support;

import cn.seu.dto.AlipayResultInDTO;
import cn.seu.dto.AlipayResultOutDTO;
import cn.seu.socket3.Client;

import javax.swing.*;

public class PollingForOrderState extends Thread{

    private static String stateFinished = "TRADE_TRADE_FINISHED";
    private static String stateSuccess = "TRADE_SUCCESS";
    private static String stateUnpay = "TRADE_UNPAY";
    private static String stateExpire = "TRADE_EXPIRE";

    private AlipayResultInDTO alipayResultIn;
    public Client a;
    private JComponent parent;

    PollingForOrderState(AlipayResultInDTO alipayResultIn, JComponent parent){
        this.alipayResultIn = alipayResultIn;
        this.parent = parent;
        a = new Client();
    }

    @Override
    public void run() {
        System.out.println("start polling! out trade no = " + this.alipayResultIn.getOutTradeNo());
        AlipayResultOutDTO state = new AlipayResultOutDTO(this.alipayResultIn.getOutTradeNo(), stateUnpay);
        while(state.getStatus().equals(stateUnpay)){
            state = (AlipayResultOutDTO) a.request(711, true, this.alipayResultIn);
            System.out.println("polling! state = " + state.getStatus());
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("polling finish! state = " + state.getStatus());
        if(state.getStatus().equals(stateSuccess)){
            JOptionPane.showMessageDialog(this.parent, "支付宝订单号为 " + state.getOutTradeNo() + " 的订单：支付成功！");
        } else if (state.getStatus().equals(stateFinished)) {
            JOptionPane.showMessageDialog(this.parent, "支付宝订单号为 " + state.getOutTradeNo() + " 的订单：已经支付过的订单！");
        } else if (state.getStatus().equals(stateExpire)) {
            JOptionPane.showMessageDialog(this.parent, "支付宝订单号为 " + state.getOutTradeNo() + " 的订单：已过期！");
        }
    }

    public static void main(String[] args) {
        PollingForOrderState p = new PollingForOrderState(new AlipayResultInDTO("1000000395535662"), null);
        p.start();
    }
}

