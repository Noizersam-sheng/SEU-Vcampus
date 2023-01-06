import java.io.IOException;

public class OpenWebURL {

    public static void main(String[] args) {
//        String url = "explorer https://www.baidu.com\n";
        String url = "explorer http://120.46.215.180:8088/goAlipay^?id=123456^&outTradeNo=321654\n";

        Process process = null;
        try {
            System.out.println("open baidu!");
            process = Runtime.getRuntime().exec(url);
            System.out.println("opened!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
