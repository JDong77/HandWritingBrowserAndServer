package controller;


import server.HttpServlet;
import server.HttpServletRequest;
import server.HttpServletResponse;
import service.UserService;

/**
 * 文件名：IndexController
 * 创建者：JiangD
 * 创建时间：2022/11/26 22:57
 * 描述：TODO
 */
public class IndexController extends HttpServlet {
    public IndexController(){
        System.out.println("类被执行了~~");
    }
    UserService userService =  new UserService();
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        //如果登录成功返回成功信息，否则返回失败信息
        String result = userService.login(name, pwd);
        System.out.println(result);
        System.out.println("被浏览器请求到的资源~");
//        response.write(result+"<br>了吗？");
        //相当于JSP
        response.write("HTTP1.1 200 OK\r\n");
        response.write("Content-Type:text/html;charset=UTF-8\r\n");
        response.write("\r\n");
        response.write("<html>");
        response.write("<body>");
        response.write("<input type='button' value = '按钮'>");
        response.write("</body>");
        response.write("</html>");
    }
}
