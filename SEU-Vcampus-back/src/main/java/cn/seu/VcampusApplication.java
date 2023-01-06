package cn.seu;

import cn.seu.socket3.ServerBack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.ServerSocket;
import java.net.Socket;

@MapperScan("cn.seu.mapper")
@SpringBootApplication
public class VcampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(VcampusApplication.class, args);
        System.out.println("在下面启动socket-----");
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerBack(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
    }
}
