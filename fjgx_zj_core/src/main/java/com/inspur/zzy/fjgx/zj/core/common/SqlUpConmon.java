package com.inspur.zzy.fjgx.zj.core.common;

public class SqlUpConmon {
    //资金系统传来付款明细  来确认那张单子修改那张单子的状态
    public static  String getSqlUpPayTailBill(String param){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select bxz.Robxdj_Nm as BZDBILLID ");
        sbf.append("FROM FSROTRAVELBILL bxz ");
        sbf.append("inner join ROBXFK paytail on bxz.ROBXDJ_NM=paytail.robxfk_bxnm ");
        sbf.append("where paytail.ROBXFK_FKNM='"+param+"' ");
        return sbf.toString();
    }


    //获取通用报销单Sql
    public static  String getUniversalBill(String param){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select bxz.Robxdj_Nm as BZDBILLID ");
        sbf.append("FROM ROBXFK bxz ");
        sbf.append("inner join ROBXFK paytail on bxz.ROBXDJ_NM=paytail.robxfk_bxnm ");
        sbf.append("where paytail.ROBXFK_FKNM='"+param+"' ");
        return sbf.toString();
    }

    //获取差旅费报销单Sql
    public static  String gettravelBill(String param){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select bxz.Robxdj_Nm as BZDBILLID ");
        sbf.append("FROM FSROTRAVELBILL bxz ");
        sbf.append("inner join ROBXFK paytail on bxz.ROBXDJ_NM=paytail.robxfk_bxnm ");
        sbf.append("where paytail.ROBXFK_FKNM='"+param+"' ");
        return sbf.toString();
    }
}
