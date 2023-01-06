package cn.seu.config;

import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

@Data
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000121666972";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDd3KPmdgOCsEI1BqNIpXY1DVGGxRShidsMAj3XiJsU2qmru+X7y4hlTzfn2olmKtFM2w5xLbkJ/CgU5daVPpdW0Fnq49xdSO+gKQQJB/7YKDVusjNMbsXqpDe8y39PPDhKljYZVnRzs+hS12DYGOnxvA2+0bziT0h7fcG1LnpSVqZGHuX6Kbwn432Jh8xboXiMqzRQH4eag663gG1ITldJOwJxw0TEntrkSW2H4KVJWvS0N257v7SNDKDyFSivi6w9k+VggRCwJxKCSne6XqlSCoGOHGsMPRAuxbdk95f/1KuBgqCfmp+UvzNQMQQIRSm87xad1ryw40fr5jhbmu5hAgMBAAECggEBAIZCu7z/tegkmixUtAOGfh5kzO1t3qpTLof540rHlHHuUbdnoinaZdRJOF/K1FNLnhWQixDaxpvQSME8xbrTgeo7c+69reGV/c1ChPqiKArPamZu2LRsZME2V7ScwZJFsxsQA/UZaKeMPA/F3wlnKkvrrbXIXzHlIMv6snregY/JV6TH9Z00NMK85kDod21RJ6x9ZV0i/OY66ch8RKIRNPcApdsT17n7VPmYioa8eZBKLMHmqf5S7Q1bAeNE5cX+vXUvR32v5f9ncOJ/+1yDgNJmf25dMPF0A25QRzkcXg9clI3KTjZ3fGo18r5y3ptdeneCP6xdgRJNwroQ+a5otEECgYEA8a5G2nG4tXamzrpjmYQfNCLY8VvKideE9xOmwSXaHmtt0rOsLWh9LGiXhd1K5ssWKUu9nKzJq7Sa9CUHN34l6z8keFLReXhiAfPRkuIaS6c7bwKSIyk+j9TeOIPwAz4g+t8pYsM6EZG4xPoeUPELKTZZaVO7KgwRNtIrchpy25UCgYEA6wHCCAoyyd5VOxMbQ02adCSXqksb5Cqv6o9WHXDu7CfKKPrH4oWAHC8hZIIUpRT6h/D8svk/Vw0DGFmmN/ikmhw55dtDsXR2SaF+3z0gDhrr4AlhVepYtXj+J7q3Vuu4DdO/pEGARKxv7FUosOGzx9h6ugcFaRNjFoyuBXBQNJ0CgYA3mXVZadIphQtpAb5KIfgfwWiRViGpRnijctTpGHEukF5qm8UfiStK3SEGvdsub0hA5djQQ3v90YXYZvBbFt1YWmVkfQEN5eD+O/5ZwUR1ZRqqNjgyeJmY80rQS6IazN8/d7mIrAse4ZxVTIlO67U6nPNC5O1pBe+r4fv2uxjzZQKBgEhgijmfqnSxnN5MzCC2TdNnUa6I41YqL3O2IXd/yJXCy2hDlDwXkZUcjtQ9m84b3+zuM2WLjg5pW4KsWZzzXv53ov0/wmFs1OFoc4KdeLnfYLXnLasgOaHqWZwyzTRf9JeNl4AnDDIaSX4n3Bhd6tipu2J/txLdfa78NLxIEuuhAoGATifM5UOM5DzmJ8LvyOI+5BoQd24Y1CAl+nwkxx83j/cGAtmBQ0dP3biRNCOitUgKjzkWAEyNrcXp/x6lGix+cH9MQLCbWin4E/YFodsSbRpOrNvFJzjOIcrbrewMa3M9dAjsgdivkkBVaQNQWM0Srtkmn41UbAUFYTkjydph9ao=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAihAzhT98QPPcsGRynAluNIW6GqwCkdDmM9cNgz38OAPg7+RBZBuGDhKeCVIdni0i38+I7gohGZJYBlvyVRWqv6Kc+nQu7lNs3Q7J7Y1bwE4edMRk+oghaDJZBtrRIpaZuKUdD0lGqMgtzLd9sIbmPnk1fVbpb2YjUDKIWiusCVH3Nlrw0rf6zwYIQNhBInaOjn3IUCxaoPBL/ARflOzv7hKAxCMCbXXBos9D8T/RoUf+svu8YTCvlkZRNN7CALrduuYLKp8Lo8bL/0rxZMuFROx9k8VXEFJ91exx5/BtBWWUa1G/mKTuxaGKpoYSim+ERNmv+JTFcP3IQhYoblg5WwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.46.215.180:8088/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://120.46.215.180:8088/";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
