package com.inspur.zzy.fjgx.fsex.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

@Slf4j
public class ReadXmlUtils {
    public static JSONObject getXmlTranJsonObject(String XML) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        //切割出返回在result中的字符串
        String result = StringUtils.substringBetween(XML, "<result>", "</result>");

        if (StringUtils.isBlank(result)){
            log.error("数据接收异常");
            throw new RuntimeException("数据接收异常");
        }
        StringBuffer append = stringBuffer.append("<result>").append(result).append("</result>");
        String strXML = append.toString();
        Document doc = null;
        doc = DocumentHelper.parseText(strXML);
        //用来接收XML格式话的参数
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject RDjsonobjecy;
        Element root = doc.getRootElement();// 指向根节点
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();// 一个Item节点
            Iterator<Element> elementIterator = element.elementIterator();
            jsonObject.put(element.getName(), element.getTextTrim());
            if ("RD".equals(element.getName())) {
                RDjsonobjecy = new JSONObject();
                while (elementIterator.hasNext()) {
                    Element RDelement = elementIterator.next();
                    RDjsonobjecy.put(RDelement.getName(), RDelement.getTextTrim());
                }
                jsonArray.add(RDjsonobjecy);
                jsonObject.put("RD", jsonArray);
            }
        }
        return jsonObject;
    }
}
