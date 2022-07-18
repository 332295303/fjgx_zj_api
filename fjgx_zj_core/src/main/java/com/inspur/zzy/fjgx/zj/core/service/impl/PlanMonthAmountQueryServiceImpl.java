package com.inspur.zzy.fjgx.zj.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.inspur.zzy.fjgx.zj.core.common.XmlConmon;
import com.inspur.zzy.fjgx.zj.core.util.ReadXmlUtils;

//0624
public class PlanMonthAmountQueryServiceImpl {
    public void PlanMonthAmountQueryServiceMethod() throws Exception {
        //首先拿到界面传过来的值
        //封装成XML格式作为参数
        String xmlPlanMonthAmountQuery = XmlConmon.getXmlPlanMonthAmountQuery("101","山西杏花村汾酒厂股份有限公司"
                ,"10003","工会","202204","0","555","1","1","1","01");
        JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(xmlPlanMonthAmountQuery);
        //判断返回值是否占用成功

        if ("1".equals(xmlTranJsonObject.getString("flag"))){
            System.out.println("成功");
        }
        else{
            throw new RuntimeException("占用失败 :"+xmlTranJsonObject.getString("mess"));
        }
    }
}
