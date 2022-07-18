package com.inspur.zzy.fjgx.zj.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.inspur.zzy.fjgx.zj.core.common.Conmon;
import com.inspur.zzy.fjgx.zj.core.common.FJZJCommon;
import com.inspur.zzy.fjgx.zj.core.common.UrlCommon;
import com.inspur.zzy.fjgx.zj.core.common.XmlConmon;
import com.inspur.zzy.fjgx.zj.core.util.ReadXmlUtils;
import com.inspur.zzy.fjgx.zj.core.util.TRTSQLUtils;
import com.inspur.zzy.fjgx.zj.core.util.WebServiceUtils;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.rmi.RemoteException;


@Slf4j
public class CapitalPlan {

    private FJZJCommon fjzjCommon;
    public CapitalPlan(FJZJCommon zjCommon) {
        this.fjzjCommon = zjCommon;
    }
    // @SneakyThrows
    public void call_method() throws IOException {
        // WebServiceUtils webServiceUtils =new WebServiceUtils();
        try {
        //拿到封装好的查询工具类
        TRTSQLUtils rktrtSqlUtils = SpringBeanUtils.getBean(TRTSQLUtils.class);
        //拿到封装成jsonArray的结果集
        JSONArray resultJsonArray = rktrtSqlUtils.getResultJsonArray(Conmon.getCapitalSql());
        UrlCommon urlCommon = new UrlCommon(fjzjCommon);
        //将结果集封装成XML对象
        if (!(resultJsonArray.size() > 0)) {
            log.error("CapitalPlan 组织列表为空");
            throw new RuntimeException("组织列表为空");
        }
        for (int i = 0; i < resultJsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);
            //此处有可能会抛出一个转换异常
            String iputCapitalPlanXml = XmlConmon.getIputCapitalPlanXml(jsonObject.getString("ENTITY_ID"), jsonObject.getString("ENTITY_NAME"),
                    jsonObject.getString("FISCAL_MONTH"), jsonObject.getString("ORIGIN_APP"), jsonObject.getString("CHECK_ID"));
            //输出调用资金  xml信息 返回值信息
            String s = WebServiceUtils.soapPost(urlCommon.urlQuerycapitalPlan(), iputCapitalPlanXml);
            //将发来的XML格式转换成JSON格式
            JSONObject xmlTranJsonObject = xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
            //若是一下错误则代表推送成功
            if ("1".equals(xmlTranJsonObject.getString("flag")) ) {
                //获取RD中的分组进行插入
                JSONArray rd = xmlTranJsonObject.getJSONArray("RD");
                if (rd != null) {
                    for (int i1 = 0; i1 < rd.size(); i1++) {
                        //拿到RD中的value值
                        JSONObject jsonObject2 = rd.getJSONObject(i1);

                        Boolean existCapitalPlanNotes = rktrtSqlUtils.getExistCapitalPlanNotes(Conmon.isExistCapitalPlanNotes(jsonObject2.getString("entity_id"),jsonObject2.getString("subject_id"),jsonObject2.getString("fiscal_month")));
                        //true存在 不进行插入 !true进行插入
                        if (!existCapitalPlanNotes) {
                            //如果该记录不存在则进行插入操作
                            rktrtSqlUtils.insertCapitalPlanSql(Conmon.insertCapitalPlanSql(
                                    jsonObject2.getString("entity_id"), jsonObject2.getString("entity_name"),
                                    jsonObject2.getString("subject_id"), jsonObject2.getString("subject_name"),
                                    jsonObject2.getString("fiscal_month"), jsonObject2.getString("amount"),
                                    jsonObject2.getString("remark")
                            ));
                        }
                    }
                }

            }

        }
        } catch (Exception e) {
            log.error("call_method 内部错误"+e);
            e.printStackTrace();
            throw new RemoteException("call_method 内部错误" + e);
        }

    }

//    public static String getReturnValue(String param) {
//        String webServiceUrl = "http://172.24.4.118/services";//接口查看地址 会变 动
//        String webServiceNam = "/PlanMonthQueryService?wsdl";//接口名称 当前的是其它接口 DEMO，此处需要根  据每个系统调用的接口不通而更换
//        String OperationName = "QueryPlanMonthQuery";//接口中具体的方法名称 当前的是其它接口 DEMO，此 处需要根据每个系统调用的接口不通而更换
//
//        try {
//            //调用登陆接口
//            Service s = new Service();
//            Call call = (Call) s.createCall();
//            //超时
//            call.setTimeout(Integer.valueOf(1000 * 600000 * 60));
//            call.setOperationName(OperationName);//调用的具体方法名称
//            call.setTargetEndpointAddress(webServiceUrl + webServiceNam);
//            call.setReturnQName(new QName("", OperationName));
//            call.setTimeout(Integer.valueOf(1000 * 600000 * 60));
//            call.setMaintainSession(true);
//            String result = (String) call.invoke(new Object[]{param});
//            return result;
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            log.error("getReturnValue 错误" + e);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            log.error("getReturnValue 错误" + e);
//        }
//        return null;
//    }


}
