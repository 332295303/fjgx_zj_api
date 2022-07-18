package com.inspur.zzy.fjgx.fsex.core.common;

public class XmlConmon {

    /*

    <ROOT>
<PAY_COMPANY_CODE>8310</PAY_COMPANY_CODE>
<PAY_COMPANY_NAME>山西杏花村汾酒厂股份有限公司</PAY_COMPANY_NAME>
<PAY_ACCOUNT_CODE>617201040000761</PAY_ACCOUNT_CODE>
<PAY_ACCOUNT_NAME>山西汾阳钢业贸易有限公司</PAY_ACCOUNT_NAME>
<REC_COMPANY_CODE>102</REC_COMPANY_CODE>

<REC_COMPANY_NAME>山西杏花村汾酒销售有限责任公司</REC_COMPANY_NAME>
<REC_ACCOUNT_CODE>618401040000733</REC_ACCOUNT_CODE>
<REC_ACCOUNT_NAME>汾阳市雷声电器经销部</REC_ACCOUNT_NAME>
<REC_HBANK_CODE>103100000026</REC_HBANK_CODE>
<REC_HBANK_NAME>中国农业银行资金清算中心</REC_HBANK_NAME>

<AMOUNT>628</AMOUNT>
<PURPOSE>20000450</PURPOSE>
<PUBLIC_FLAG>1</PUBLIC_FLAG>
<DOCUMENT_NO>555</DOCUMENT_NO>
<CHECK_ID>20000450</CHECK_ID>

<PAYMENT_MODE>01</PAYMENT_MODE>
<SUBMIT_NAME>赵五</SUBMIT_NAME>
<SUBMIT_DEPT>GEMS</SUBMIT_DEPT>
<ORIGIN_APP>01</ORIGIN_APP>
</ROOT>
     */
    //付款明细接口
    public static String getPayBillEntryXml(String REC_COMPANY_CODE ,String REC_COMPANY_NAME,String REC_ACCOUNT_CODE,
    String REC_ACCOUNT_NAME,String REC_HBANK_CODE,String REC_HBANK_NAME,
    String AMOUNT,String PURPOSE, String PUBLIC_FLAG,String CHECK_ID,String OCCUPY_ID
    ){
        StringBuffer sbf = new StringBuffer();
        sbf.append("<REC>");
        //供应商编码
        sbf.append("<REC_COMPANY_CODE>").append(REC_COMPANY_CODE).append("</REC_COMPANY_CODE>");
        //供应商名称
        sbf.append("<REC_COMPANY_NAME>").append(REC_COMPANY_NAME).append("</REC_COMPANY_NAME>");
        //收款账号
        sbf.append("<REC_ACCOUNT_CODE>").append(REC_ACCOUNT_CODE).append("</REC_ACCOUNT_CODE>");
        //收款户名
        sbf.append("<REC_ACCOUNT_NAME>").append(REC_ACCOUNT_NAME).append("</REC_ACCOUNT_NAME>");
       // 收款开户行人行号
        sbf.append("<REC_HBANK_CODE>").append(REC_HBANK_CODE).append("</REC_HBANK_CODE>");
        //收款开户行人行名
        sbf.append("<REC_HBANK_NAME>").append(REC_HBANK_NAME).append("</REC_HBANK_NAME>");
        //交易金额
        sbf.append("<AMOUNT>").append(AMOUNT).append("</AMOUNT>");
        //事由
        sbf.append("<PURPOSE>").append(PURPOSE).append("</PURPOSE>");
       // 对公标志
        sbf.append("<PUBLIC_FLAG>").append(PUBLIC_FLAG).append("</PUBLIC_FLAG>");
        //数据行主键
        sbf.append("<CHECK_ID>").append(CHECK_ID).append("</CHECK_ID>");
        sbf.append("<OCCUPY_ID>").append(OCCUPY_ID).append("</OCCUPY_ID>");
        sbf.append("</REC>");

        return sbf.toString();
    }
    //付款单接口
    public static  String  getPayBillXml(
         String PAY_COMPANY_CODE,String PAY_COMPANY_NAME,String PAY_ACCOUNT_CODE,
         String PAY_ACCOUNT_NAME,
         //String REC_COMPANY_CODE,String REC_COMPANY_NAME,
        // String REC_ACCOUNT_CODE,String REC_ACCOUNT_NAME,String REC_HBANK_CODE,
         //String REC_HBANK_NAME,String AMOUNT,String PURPOSE,
         //String PUBLIC_FLAG,String CHECK_ID,
         String DOCUMENT_NO,
         String PAYMENT_MODE,String SUBMIT_NAME,String SUBMIT_DEPT,String ORIGIN_APP,
         String DOCUMENT_ID,StringBuffer sbfEntry
    ){
        StringBuffer sbf = new StringBuffer();
        sbf.append("<ROOT>");
        sbf.append("<PAY_COMPANY_CODE>").append(PAY_COMPANY_CODE).append("</PAY_COMPANY_CODE>");
        sbf.append("<PAY_COMPANY_NAME>").append(PAY_COMPANY_NAME).append("</PAY_COMPANY_NAME>");
        sbf.append("<PAY_ACCOUNT_CODE>").append(PAY_ACCOUNT_CODE).append("</PAY_ACCOUNT_CODE>");
        sbf.append("<PAY_ACCOUNT_NAME>").append(PAY_ACCOUNT_NAME).append("</PAY_ACCOUNT_NAME>");
        //sbf.append("<REC_COMPANY_CODE>").append(REC_COMPANY_CODE).append("</REC_COMPANY_CODE>");

        //sbf.append("<REC_COMPANY_NAME>").append(REC_COMPANY_NAME).append("</REC_COMPANY_NAME>");
        //sbf.append("<REC_ACCOUNT_CODE>").append(REC_ACCOUNT_CODE).append("</REC_ACCOUNT_CODE>");
        //sbf.append("<REC_ACCOUNT_NAME>").append(REC_ACCOUNT_NAME).append("</REC_ACCOUNT_NAME>");
        //sbf.append("<REC_HBANK_CODE>").append(REC_HBANK_CODE).append("</REC_HBANK_CODE>");
        //sbf.append("<REC_HBANK_NAME>").append(REC_HBANK_NAME).append("</REC_HBANK_NAME>");

       // sbf.append("<AMOUNT>").append(AMOUNT).append("</AMOUNT>");
        //sbf.append("<PURPOSE>").append(PURPOSE).append("</PURPOSE>");
        //sbf.append("<PUBLIC_FLAG>").append(PUBLIC_FLAG).append("</PUBLIC_FLAG>");
        sbf.append("<DOCUMENT_NO>").append(DOCUMENT_NO).append("</DOCUMENT_NO>");
        //sbf.append("<CHECK_ID>").append(CHECK_ID).append("</CHECK_ID>");

        sbf.append("<PAYMENT_MODE>").append(PAYMENT_MODE).append("</PAYMENT_MODE>");
        sbf.append("<SUBMIT_NAME>").append(SUBMIT_NAME).append("</SUBMIT_NAME>");
        sbf.append("<SUBMIT_DEPT>").append(SUBMIT_DEPT).append("</SUBMIT_DEPT>");
        sbf.append("<ORIGIN_APP>").append(ORIGIN_APP).append("</ORIGIN_APP>");
        sbf.append("<DOCUMENT_ID>").append(DOCUMENT_ID).append("</DOCUMENT_ID>");

        //新加
        sbf.append("<RECS>");
        sbf.append(sbfEntry.toString());
        sbf.append("</RECS>");

        sbf.append("</ROOT>");
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:erp=\"http://erpif.fc.mpc.ermsuite.neusoft.com\">\n" +
                "   <soapenv:Header>\n" +
                "   </soapenv:Header>\n" +
                "   <soapenv:Body>\n" +
                "      <erp:pushBillAP soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <inXML xsi:type=\"soapenc:string\" xs:type=\"type:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xs=\"http://www.w3.org/2000/XMLSchema-instance\"><![CDATA["+sbf.toString()+"]]></inXML>\n" +
                "      </erp:pushBillAP>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";


        return body;
    }


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


    //资金占用 释放  接口地址  及参数  0是占用 1是释放
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
