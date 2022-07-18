import com.inspur.zzy.fjgx.common.core.service.FJHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonTest {
    public static void main(String[] args){
        String para = "12";
        String url ="http://172.24.4.118/services/PlanMonthQueryService";
        try {
            Map<String, String> header = new HashMap<>();
            header.put("SOAPAction", "");
            String res = FJHttpUtils.post(url, para, "text/xml", header);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error:" + e.getMessage());
        }

    }
}
