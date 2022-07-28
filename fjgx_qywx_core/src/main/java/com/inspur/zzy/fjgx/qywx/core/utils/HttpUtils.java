package com.inspur.zzy.fjgx.qywx.core.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
@Slf4j
public class HttpUtils {
    //get请求返回  token

    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
                System.out.println("112"+result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }






    public static JSONObject doPost(String url,String paramJson) throws IOException {
        //post请求返回结果
        JSONObject jsonResult = null;
                log.info("请求url"+url);
        log.info("请求参数"+paramJson);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String str = "";
        HttpPost method = new HttpPost(url);
        try {
            if (null != paramJson) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(paramJson.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                entity.setContentType("application/jsonarray;charset=utf-8");
                method.setHeader("Content-Type","application/json; charset=UTF-8");
                method.setHeader("Connection","keep-alive");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);

            url = URLDecoder.decode(url, "UTF-8");

            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {

                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/
                    jsonResult=(JSONObject)JSONObject.parse(str);
                    return jsonResult;
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("企业微信请求返回参数"+e.getMessage());
                    throw e;
                }
            }
            log.error("企业微信请求返回参数"+str);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("企业微信请求返回参数"+e.getMessage());
            throw e;
        }
        return jsonResult;

    }



}
