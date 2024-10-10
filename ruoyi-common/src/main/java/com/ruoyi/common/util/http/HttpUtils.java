package com.ruoyi.common.util.http;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
    // 调用返回一个与HttpUtils类关联的Logger实例,日志消息将会被标记为来自HttpUtils类
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);


    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param)
    {
        return sendGet(url, param, Constants.UTF8);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = StringUtil.isNotBlank(param) ? url + "?" + param : url;
            log.info("sendGet - {}", urlNameString);
            // 需要有 catch (Exception e)，否则错
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            // connection.getInputStream(): 获取从服务器返回的响应体数据流
            // InputStreamReader: 读取字节，并使用指定的字符集将其解码为字符
            // BufferedReader: 从字符输入流中读取文本，并且可以提供缓冲功能以提高读取效率
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;

            // 只要不是空的，就补上
            // readLine: 按行读取文本
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        }
        catch( ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + "param" + param, e);
        }
        catch (SocketTimeoutException e)
        {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        }
        catch (IOException e)
        {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        }
        catch (Exception e)
        {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }
}
