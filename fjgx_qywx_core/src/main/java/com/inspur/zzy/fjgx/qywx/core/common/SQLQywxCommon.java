package com.inspur.zzy.fjgx.qywx.core.common;

public class SQLQywxCommon {
    public static String getUserCode(String userid){
        StringBuffer bf = new StringBuffer();
        bf.append("select  us.code CODE ");
        bf.append("from bfemployee  bfy ");
        bf.append("inner join gspuser us on bfy.Timestamp_creaTedby=us.id ");
        bf.append("where bfy.id='"+userid+"' ");
        return bf.toString();
    }
}
