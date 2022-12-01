package server;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：Handler
 * 创建者：JiangD
 * 创建时间：2022/11/29 15:35
 * 描述：服务器只有启动服务器后不能停掉，
 * 如果来了一个浏览器访问，需要启动一个服务器线程去处理当前浏览器的请求与响应
 * 服务器然后等待下一个浏览器访问
 */
public class Handler extends Thread{
    private Socket socket;
    public Handler(Socket socket){
        this.socket = socket;
    }
    public void run(){
    receiveRequest();
    }
    //单例模式,为了管理Controller中的对象单例
    private Map<String,HttpServlet> controllerMap = new HashMap<>();

    //1、启动浏览器
    //2、等待浏览器连接 socket
//    public void start(){
//        try {
//
//            receiveRequest();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
    //3、读取浏览器发过来的请求信息 content
    private void receiveRequest(){
        //创建读取信息的输入流
        try {
            //创建读取信息的输入流
            InputStream inputStream = socket.getInputStream();
            //将字节流转化为字符流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            //字符流包装为Buffered缓冲流（读一行）
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //读取浏览器发送的一行请求信息   URL
            String content = bufferedReader.readLine();
            //拿到请求信息
            System.out.println(content);
            //解析请求 127.0.0.1:9999/index.html?name=jiang&pwd=123&email=888
            parseContent(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //4、解析请求方法
    private void parseContent(String content){
        //如果换成真正的浏览器,重新写解析规则
        //读取浏览器第一行信息
        //GET /index?name=jiang&pwd=123 HTTP/1.1
        String[] vvv = content.split(" ");
        if (vvv.length>0) {
            content = vvv[1].substring(1);
        }

        String requestName = null;//请求路径中&前半部分资源 127.0.0.1:9999/index.html
        //请求路径中&后半部分资源 {name=jiang，pwd=123，email=888}
        Map parameterMap = null; //懒加载
        //处理数据  127.0.0.1:9999/index.html?name=jiang&pwd=123&email=888
        int index = content.indexOf("?");
        if (index != -1) {//？号后面有值
            requestName = content.substring(0,index); //127.0.0.1:9999/index.html
            System.out.println(requestName);
            String s = content.substring(index + 1);//name=jiang&pwd=123&email=888
            String[] split = s.split("&");
            parameterMap = new HashMap<>();//懒加载实现，此处创建对象，节省内存空间，提高程序启动响应速度
            for (String s1 : split) {
                String[] split1 = s1.split("=");
                parameterMap.put(split1[0],split1[1]);
            }
        }else { //？号后面没有值,没有参数
            requestName = content;
        }
        System.out.println(parameterMap);
        //将解析好的数据存入对象
        HttpServletRequest request = new HttpServletRequest(requestName,parameterMap);
        HttpServletResponse response = new HttpServletResponse();
        //调用方法
        findServlet(request,response);
    }
    //5、用请求名字照找寻资源（文件/操作） Java类（方法--->做事）
    private void findServlet(HttpServletRequest request,HttpServletResponse response) {
//        常规访问方式
//        IndexController indexController = new IndexController();
//        indexController.test();
        //将服务器和资源进行区分，此类相当于Tomcat服务器，将资源和资源需要定位的位置单独划分
        //浏览器发请求  服务/资源注册到Tomcat服务器  请求过来以后请求Tomcat Tomcat来做定位
        //这样设计的目的是为了将服务器单独封装，开发者无需手写服务器，只需专心于业务逻辑和配置文件的书写---->M，C（MVC）

        try {
            //1.获取request中的对象请求名
            String requestName = request.getRequestName();
            HttpServlet servlet = controllerMap.get(requestName);
            if (servlet == null){
                //3.通过properties集合里面取出来的文件记录找寻类全名
                //String className = properties.getProperty(requestName);
                String className = MyServerReader.getValue(requestName);
                //4.通过类名字反射加载类 （工厂模式）
                Class<?> claszz = Class.forName(className);
                //5.通过Classzz创建对象
                Constructor<?> constructor = claszz.getConstructor();
                //（工厂模式）
                servlet = (HttpServlet) constructor.newInstance();
                controllerMap.put(requestName,servlet);
            }
            //第一种
            servlet.service(request,response);
            //第二种
//            Class<? extends HttpServlet> aClass = servlet.getClass();
//            Method method = aClass.getMethod("service", HttpServletRequest.class);
//            method.invoke(servlet,request);
            //将响应信息写回浏览器中
            writeBrowser(response);


            //2.参考“说明书”读取配置文件 通过请求名得到真实类全名
            //Properties属于集合框架，其继承自Hash Table
//            Properties properties = new Properties();
//            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("web.properties");
//            properties.load(resourceAsStream);
//            //3.通过properties集合里面取出来的文件记录找寻类全名
//            //String className = properties.getProperty(requestName);
//            String className = MyServerReader.getValue(requestName);
//
//            //4.通过类名字反射加载类 （工厂模式）
//            Class<?> claszz = Class.forName(className);
//            //5.通过Classzz创建对象
//            Constructor<?> constructor = claszz.getConstructor();
//            //（工厂模式）
//            Object o = constructor.newInstance();
//            //6.通过方法找寻类中需要执行的方法
//            Method method = claszz.getMethod("service",HttpServletRequest.class);
//            //7.执行方法
//            method.invoke(o,request);
        } catch (Exception e) {
            throw   new RuntimeException(e);
        }

//        System.out.println(request.getParameterMap());
//        System.out.println(request.getRequestName());
//        System.out.println(request.getParameter("name"));
    }

    //6、资源执行完的结果，写回浏览器 响应
    public void writeBrowser(HttpServletResponse response){
        String responseContent = response.getResponseContent();
        try {
            OutputStream outputStream = socket.getOutputStream();//字节流
            PrintWriter writer = new PrintWriter(outputStream);//包装成字符流
            writer.println(responseContent);//真正写回浏览器的位置
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
