package server;

import java.util.Map;

/**
 * 文件名：server.HttpServletRequest
 * 创建者：JiangD
 * 创建时间：2022/11/26 18:22
 * 描述：TODO
 */
public class HttpServletRequest {
    private String requestName;
    private Map<String,String> parameterMap;

    public HttpServletRequest(String requestName,Map parameterMap){
        this.requestName = requestName;
        this.parameterMap = parameterMap;
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public String getRequestName() {
        return requestName;
    }
    public String getParameter(String key){
        return parameterMap.get(key);
    }
}
