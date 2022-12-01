package server;

import controller.IndexController;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 文件名：server.Server
 * 创建者：JiangD
 * 创建时间：2022/11/24 15:32
 * 描述：TODO
 */
public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("===server.Server Start===");
        //接收端口为9999的socket连接
        ServerSocket serverSocket = new ServerSocket(9999);
        //等待接收请求
        while (true){
            Socket  socket = serverSocket.accept();
            Handler handler = new Handler(socket);
            handler.start();
        }
//        //写一个简易版服务器，读取真正浏览器发来的请求
//        ServerSocket server = new ServerSocket(9999);
//        Socket socket = server.accept();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        String value = reader.readLine();
//        while ("" != value && value != null){
//            System.out.println(value);
//            value = reader.readLine();
//        }
//        GET /index?name=jiang&pwd=123 HTTP/1.1
//        Host: 127.0.0.1:9999
//        Connection: keep-alive
//        Cache-Control: max-age=0
//        sec-ch-ua: "Microsoft Edge";v="107", "Chromium";v="107", "Not=A?Brand";v="24"
//        sec-ch-ua-mobile: ?0
//        sec-ch-ua-platform: "Windows"
//        Upgrade-Insecure-Requests: 1
//        User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.56
//        Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
        //Sec-Fetch-Site: none
        //Sec-Fetch-Mode: navigate
        //Sec-Fetch-User: ?1
        //Sec-Fetch-Dest: document
        //Accept-Encoding: gzip, deflate, br
        //Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6

//        new Server().start();
    }

}
