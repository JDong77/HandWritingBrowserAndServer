package server;

/**
 * 文件名：HttpServlet
 * 创建者：JiangD
 * 创建时间：2022/11/28 0:22
 * 描述：TODO
 */
public abstract class HttpServlet {
    public abstract void service(HttpServletRequest request,HttpServletResponse response);
}
