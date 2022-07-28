package com.inspur.zzy.fjgx.qywx.core.common;

import com.alibaba.fastjson.JSONObject;

public class QywxCommon {
    public static String getBody(String touser,String agentid,String msgtype,
                                 String safe,String enable_id_trans,String enable_duplicate_check,
                                 String duplicate_check_interval,String totag,String toparty,
                                 String content){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser",touser);
        jsonObject.put("agentid",agentid);
        jsonObject.put("msgtype",msgtype);
        jsonObject.put("safe",Integer.parseInt(safe));
        jsonObject.put("enable_id_trans",Integer.parseInt(enable_id_trans));
        jsonObject.put("enable_duplicate_check",Integer.parseInt(enable_duplicate_check));
        jsonObject.put("duplicate_check_interval",Integer.parseInt(duplicate_check_interval));
        jsonObject.put("totag",totag);
        jsonObject.put("toparty",toparty);
        JSONObject json2 = new JSONObject();
        json2.put("content",content);
        jsonObject.put("text",json2);

        return jsonObject.toString();
    }
}
