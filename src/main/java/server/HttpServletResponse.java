package server;

/**
 * 文件名：HttpServletResponse
 * 创建者：JiangD
 * 创建时间：2022/11/29 11:06
 * 描述：定义这个类的目的是为了当作容器用来装载Controller执行后的结果
 */
public class HttpServletResponse {

    private StringBuffer responseContent = new StringBuffer();
    //该方法目的是拼接字符串
    public void write(String message) {
        responseContent.append(message);
    }
    //该方法获取responseContent属性中的内容
    public String getResponseContent(){
        return responseContent.toString();
    }
}
