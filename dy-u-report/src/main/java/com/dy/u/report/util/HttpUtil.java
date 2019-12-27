package com.dy.u.report.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description HTTP请求处理类，获取请求中JSON格式的字符串
 * @author dxy
 * @date 2019-12-04
 */
public class HttpUtil {
    /**
     *
     * @param request
     *      HTTP请求
     * @return
     *      HTTP请求中的JSON内容，以字符串的格式返回
     * @throws IOException
     *      如果获取JSON字符串出错，则报异常
     */
    public static String getJsonFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
