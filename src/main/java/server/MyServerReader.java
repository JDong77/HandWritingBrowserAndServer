package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 文件名：MyServerReader
 * 创建者：JiangD
 * 创建时间：2022/11/28 0:29
 * 描述：TODO
 */
public class MyServerReader {
    //存储url中属性信息
    private static Map<String,String> map = new HashMap<>();
    static {
        //读取配置文件放到类中单独读取，也就是内存读取，速度快！
        Properties properties = new Properties();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("web.properties");
        try {
            properties.load(resourceAsStream);
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                map.put(key,value);
            }
            resourceAsStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //获取集合map集合中的数据的方法
    public static String getValue(String key){
        return map.get(key);
    }
}
