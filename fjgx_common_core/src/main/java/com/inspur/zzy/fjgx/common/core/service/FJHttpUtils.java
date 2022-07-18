package com.inspur.zzy.fjgx.common.core.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FJHttpUtils {
    public static String get(String url) throws IOException {
        return get(url, null);
    }

    public static String get(String url, Map<String, String> headers) throws IOException {
        return fetch("GET", url, null, "", headers);
    }

    public static String post(String url, String body, String contentType, Map<String, String> headers) throws IOException {
        return fetch("POST", url, body, contentType, headers);
    }

    public static String post(String url, String body, String contentType) throws IOException {
        return post(url, body, contentType, null);
    }

    static String fetch(String method, String url, String body, String contentType,
                                Map<String, String> headers) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("content-Type", contentType);
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

// method
        if (method != null) {
            conn.setRequestMethod(method);
        }

// headers
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.addRequestProperty(key, headers.get(key));
            }
        }

// body
        if (body != null) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();
        }

// response

        InputStream is = conn.getInputStream();
        String response = streamToString(is);
        is.close();

// handle redirects
        if (conn.getResponseCode() == 301) {
            String location = conn.getHeaderField("Location");
            return fetch(method, location, body, contentType, headers);
        }

        return response;
    }

    static String streamToString(InputStream in) throws IOException {

        BufferedReader inR = new BufferedReader(new InputStreamReader(in, "utf-8"));
        StringBuffer out = new StringBuffer();
        String line = "";
        while ((line = inR.readLine()) != null){
            out.append(line);
        }
        return out.toString();
    }
}
