package com.inspur.zzy.fjgx.fsex.core.utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

@Slf4j
@Data
public class WebServiceUtils {
//    public static String  webServiceUrl = "http://172.24.4.118/services";//接口查看地址 会变 动
//    public static String  webServiceNam = "/PlanMonthQueryService?wsdl";//接口名称 当前的是其它接口 DEMO，此处需要根  据每个系统调用的接口不通而更换
//    public  static String OperationName = "QueryPlanMonthQuery";//接口中具体的方法名称 当前的是其它接口 DEMO，此 处需要根据每个系统调用的接口不通而更换
//
//    //param 传入参数
//    public static String getReturnValue(String param) {
//
//        try {
//            //调用登陆接口
//            Service s = new Service();
//            Call call = (Call) s.createCall();
//            //超时
//            call.setTimeout(Integer.valueOf(1000 * 600000 * 60));
//            call.setOperationName(OperationName);//调用的具体方法名称
//            call.setTargetEndpointAddress(webServiceUrl + webServiceNam);
//            call.setReturnQName(new QName("", OperationName));
//            call.setTimeout(Integer.valueOf(1000 * 600000 * 60));
//            call.setMaintainSession(true);
//            String result = (String) call.invoke(new Object[]{param});
//            return result;
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            log.error("getReturnValue 错误" + e);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            log.error("getReturnValue 错误" + e);
//        }
//        return null;
//    }



public static String soapPost(String strUrl, String body) throws IOException {
    PrintWriter printWriter = null;
    BufferedReader bufferedReader = null;
    StringBuffer responseResult = new StringBuffer();
    HttpURLConnection httpURLConnection = null;
    //第一步：创建服务地址，不是WSDL地址
    try {
        URL url = new URL(strUrl);
        //第二步：打开一个通向服务地址的连接
        httpURLConnection = (HttpURLConnection) url.openConnection();
        //第三步：设置参数
        // 设置通用的请求属性
        httpURLConnection.setConnectTimeout(20 * 1000);
        httpURLConnection.setReadTimeout(30 * 1000);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("content-type", "text/xml;charset=utf-8");
        //请求头必须设置SOAPAction
        httpURLConnection.setRequestProperty("SOAPAction", "application/soap+xml; charset=utf-8");
        // 发送POST请求必须设置如下两行
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        printWriter = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8"));
        printWriter.write(body);
        // flush输出流的缓冲
        printWriter.flush();
        //第五步：接收服务端响应，打印
        int responseCode = httpURLConnection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (200 == responseCode) {//表示服务端响应成功
            // 定义BufferedReader输入流来读取URL的ResponseData
            bufferedReader = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream(), Charset.forName("UTF-8")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseResult.append(line);
            }
            String s = StringEscapeUtils.unescapeHtml4(responseResult.toString());
            return s;
        } else {
            throw new RuntimeException("调用webservice失败：服务器端返回HTTP code " + responseCode + "信息：");
        }

    } catch (Exception e) {
        log.error("调用资金接口异常"+e);
        throw  new RuntimeException("调用资金接口异常");
    } finally {
        try {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }

        } catch (IOException ex) {
            log.error("调用资金接口异常"+ex);
                throw  new RuntimeException("调用资金接口异常");
        }
    }



}


}

