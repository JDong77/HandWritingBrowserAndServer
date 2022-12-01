package browser;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 文件名：Browser.Client
 * 创建者：JiangD
 * 创建时间：2022/11/24 14:51
 * 描述：TODO
 */
public class Client {
    public static void main(String[] args) {
//        //主动请求发起连接
//        try {
//            Socket socket = new Socket("localhost",9999);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        new Client().open();
    }
    private Socket socket;
    //1、打开浏览器
    //2、输入url  ip：port/content
    public void open(){
        System.out.println("===打开浏览器===");
        System.out.print("请输入URL:");
        Scanner input = new Scanner(System.in);
        String url = input.nextLine();
        parseUrl(url);
    }

    //3、解析url  IP:port/content
    private void parseUrl(String url){
        int index = url.indexOf(":");
        int index1 = url.indexOf("/");
        String ip = url.substring(0, index);
        int port = Integer.parseInt(url.substring(index+1, index1));
        String content = url.substring(index1+1);
        createSocketAndSendRequest(ip,port,content);
    }
    //4、创建连接
    //5、发送请求（写OUT给服务器）
    private void createSocketAndSendRequest(String ip, int port, String content) {
        try {
            //创建socket连接
            socket = new Socket(ip,port);
            //获取输出流
            OutputStream outputStream = socket.getOutputStream();
            //字节输入流包装成字符流
            PrintWriter writer = new PrintWriter(outputStream);//装饰者模式
            //输出字符流
            writer.println(content);
            //关闭流管道
            writer.flush();
            //等着服务器响应信息
            readResponseContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //6、读取服务器写回来的响应信息
    private void readResponseContent(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseContent = bufferedReader.readLine();
            parseResponseContent(responseContent);
        } catch (IOException e) {
            throw new RuntimeException(e);                                                          
        }
    }
    //7、解析响应信息、浏览器中展示出来
    private void parseResponseContent(String responseContent){
        int index = responseContent.indexOf("<");
        int index1 = responseContent.indexOf(">");
        if (index != -1 && index1 != -1 && index < index1){
            String content = responseContent.substring(index + 1, index1);
            if (content.equals("br")){
                responseContent = responseContent.replace("<br>","\r\n");
            }
        }
        System.out.println(responseContent);
    }
}
