package com.inspur.zzy.fjgx.zj.core.common;

public class XmlConmon {

    //资金计划查询接口
    //return String 调用参数
    public static String getIputCapitalPlanXml (String ENTITY_ID,String ENTITY_NAME,String FISCAL_MONTH,
                                               String ORIGIN_APP,String CHECK_ID){
        StringBuffer sbf = new StringBuffer();
        sbf.append("<ROOT>");
        sbf.append("<ENTITY_ID>").append(ENTITY_ID).append("</ENTITY_ID>");
        sbf.append("<ENTITY_NAME>").append(ENTITY_NAME).append("</ENTITY_NAME>");
        sbf.append("<FISCAL_MONTH>").append(FISCAL_MONTH).append("</FISCAL_MONTH>");
        sbf.append("<ORIGIN_APP>").append(ORIGIN_APP).append("</ORIGIN_APP>");
        sbf.append("<CHECK_ID>").append(CHECK_ID).append("</CHECK_ID>");
        sbf.append("</ROOT>");
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:erp=\"http://erpif.fc.mpc.ermsuite.neusoft.com\">\n" +
                "   <soapenv:Header>\n" +
                "   </soapenv:Header>\n" +
                "   <soapenv:Body>\n" +
                "      <erp:QueryPlanMonthQuery soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <inXML xsi:type=\"soapenc:string\" xs:type=\"type:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xs=\"http://www.w3.org/2000/XMLSchema-instance\"><![CDATA["+sbf.toString()+"]]></inXML>\n" +
                "      </erp:QueryPlanMonthQuery>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        return body.toString();
    }


    //资金占用接口地址  及参数
    public static String getXmlPlanMonthAmountQuery(String ENTITY_ID,String ENTITY_NAME,String SUBJECT_ID,
                                                    String SUBJECT_NAME,String FISCAL_MONTH,String PROCESS_FLAG,
                                                    String DOCUMENT_NO,String AMOUNT,String REMARK,
                                                    String CHECK_ID,String ORIGIN_APP){
        StringBuffer sbf = new StringBuffer();
        sbf.append("<ROOT>");
        //公司代码
        sbf.append("<ENTITY_ID>").append(ENTITY_ID).append("</ENTITY_ID>");
        //公司名称
        sbf.append("<ENTITY_NAME>").append(ENTITY_NAME).append("</ENTITY_NAME>");
        //计划项目编码
        sbf.append("<SUBJECT_ID>").append(SUBJECT_ID).append("</SUBJECT_ID>");
        //计划项目名称
        sbf.append("<SUBJECT_NAME>").append(SUBJECT_NAME).append("</SUBJECT_NAME>");
        //年月
        sbf.append("<FISCAL_MONTH>").append(FISCAL_MONTH).append("</FISCAL_MONTH>");
        //占用/释放标识
        sbf.append("<PROCESS_FLAG>").append(PROCESS_FLAG).append("</PROCESS_FLAG>");
        //报账单号
        sbf.append("<DOCUMENT_NO>").append(DOCUMENT_NO).append("</DOCUMENT_NO>");
        //交易金额
        sbf.append("<AMOUNT>").append(AMOUNT).append("</AMOUNT>");
        //备注
        sbf.append("<REMARK>").append(REMARK).append("</REMARK>");
        //数据行主键
        sbf.append("<CHECK_ID>").append(CHECK_ID).append("</CHECK_ID>");
        //数据来源系统
        sbf.append("<ORIGIN_APP>").append(ORIGIN_APP).append("</ORIGIN_APP>");
        sbf.append("</ROOT>");
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:erp=\"http://erpif.fc.mpc.ermsuite.neusoft.com\">\n" +
                "   <soapenv:Header>\n" +
                "   </soapenv:Header>\n" +
                "   <soapenv:Body>\n" +
                "      <erp:PlanMonthAmountQuery soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <inXML xsi:type=\"soapenc:string\" xs:type=\"type:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xs=\"http://www.w3.org/2000/XMLSchema-instance\"><![CDATA["+sbf.toString()+"]]></inXML>\n" +
                "      </erp:PlanMonthAmountQuery>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return body;
    }

}
