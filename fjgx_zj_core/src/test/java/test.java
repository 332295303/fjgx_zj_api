import com.alibaba.fastjson.JSONObject;
import com.inspur.zzy.fjgx.zj.core.util.ReadXmlUtils;
import org.apache.commons.lang3.StringUtils;


public class test {
    public static void main(String[] args) throws Exception {
        String s="<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><ns1:QueryPlanMonthQueryResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"http://erpif.fc.mpc.ermsuite.neusoft.com\"><QueryPlanMonthQueryReturn xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"><?xml version=\"1.0\" encoding=\"UTF-8\"?><result>   <check_id>93453fb2-7be4-82c1-9395-0e8d959b3aa8</check_id>   <origin_app>1</origin_app>   <flag>1</flag>   <mess>推送成功</mess></result></QueryPlanMonthQueryReturn></ns1:QueryPlanMonthQueryResponse></soapenv:Body></soapenv:Envelope>";
        StringBuffer stringBuffer = new StringBuffer();
        String s1 = StringUtils.substringBetween(s, "<result>", "</result>");
        System.out.println(s1);



    }
}
