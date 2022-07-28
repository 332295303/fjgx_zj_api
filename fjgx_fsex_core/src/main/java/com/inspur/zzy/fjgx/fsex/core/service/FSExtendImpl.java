package com.inspur.zzy.fjgx.fsex.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inspur.zzy.fjgx.fsex.api.FSExtend;
//import com.inspur.zzy.fjgx.fsex.api.service.FSExtend;
import com.inspur.zzy.fjgx.fsex.core.common.*;
import com.inspur.zzy.fjgx.fsex.core.utils.ReadXmlUtils;
import com.inspur.zzy.fjgx.fsex.core.utils.TRTSQLUtils;
import com.inspur.zzy.fjgx.fsex.core.utils.WebServiceUtils;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j

public class FSExtendImpl implements FSExtend {
    @Autowired
    private FJFSExCommon fjfsExCommon;
    public FSExtendImpl(FJFSExCommon fscommon) {
        this.fjfsExCommon = fscommon;
    }
    TRTSQLUtils rktrtSqlUtils = SpringBeanUtils.getBean(TRTSQLUtils.class);

    @Override
    public Map<String, Object> submitCLbefore(Map<String, Object> paraMap) {
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        log.debug("submitCLbefore");
        Map<String, Object> result = new HashMap<>();
        for(Map.Entry<String, Object> entry: paraMap.entrySet()) {
            log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
        }
        result.put("result", true);
        result.put("Message", "");
        result.put("Code", 1);
        result.put("value", "");
        return result;
        // return null;
    }


    //报账单  1编辑 界面提交前
    @Override
    public Map<String, Object> RSSubmitAfter(Map<String, Object> paraMap) {
        System.out.println("RSSubmitBefore");
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "提交成功");
        result.put("Code", 1);
        result.put("value", "");
        Boolean flag=true;
        if (("RS".equals(paraMap.get("funcSource")) || "SSP".equals(paraMap.get("funcSource")))) {
            System.out.println("1");
//        log.debug("submit");
            for(Map.Entry<String, Object> entry: paraMap.entrySet()) {
                log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
//            HashMap stringToMap = (HashMap) entry.getValue();
                if ("billId".equals(entry.getKey())) {
                    //查询系统中的这个单据
                    JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlUniversalill(entry.getValue().toString()));
                    if(jsonArray.size()<1){

                        jsonArray=  rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlBaoZhang(entry.getValue().toString()));
                        flag=false;
                    }
                    int size = jsonArray.size();
                    if (jsonArray.size() >= 1) {
                        for (int i = 0; i < size; i++) {
                            //拿到报账单中要传递的信息
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误
                            if (!jsonObject.getString("FISCAL_MONTH").equals(jsonObject.getString("BIZDATE"))){
                                result.put("result", false);
                                result.put("Message", "填报时间错误 资金计划选择不正确");
                                result.put("Code", 0);
                                result.put("value", "");
                                log.debug("submit");
                                log.error("判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误");
                                return result;
                            }
                            //公司代码
                            String ENTITY_ID = jsonObject.getString("ENTITY_ID");
                            //公司名称
                            String ENTITY_NAME = jsonObject.getString("ENTITY_NAME");
                            //计划项目编码
                            String SUBJECT_ID = jsonObject.getString("SUBJECT_ID");
                            //计划项目名称
                            String SUBJECT_NAME = jsonObject.getString("SUBJECT_NAME");
                            //年月
                            String FISCAL_MONTH=  jsonObject.getString("FISCAL_MONTH");
                            //String FISCAL_MONTH ="202204";
                            //占用/释放标识
                            String PROCESS_FLAG = "0";
//                    String FISCAL_MONTH= jsonObject.getString("ROBXDJ_RQ");

                            //报账单号
                            String DOCUMENT_NO = jsonObject.getString("DOCUMENT_NO");
                            //是否需要控制资金计划
                            String ISNEED = jsonObject.getString("ISNEED");
                            //当1 时候假设不需要资金计划控制那么 就是全部 金额
                            String AMOUNT="";
                            if("不控制".equals(ISNEED)){
                                AMOUNT = jsonObject.getString("AMOUNT");
                            }else {
                                //实付金额
                                AMOUNT = jsonObject.getString("AMOUNT");
                                BigDecimal bigDecimal=new BigDecimal(AMOUNT);
                                //当金额大于三十w时候 ze取30
                                if (bigDecimal.compareTo(new BigDecimal(fjfsExCommon.getPjqsje()))==1){
                                    BigDecimal multiply = bigDecimal.multiply(new BigDecimal(fjfsExCommon.getXhzfbl()));
                                    bigDecimal= multiply;
                                    AMOUNT=bigDecimal.toString();
                                }
                            }
                            //备注
                            String REMARK = jsonObject.getString("REMARK");
                            //数据行主键
                            String CHECK_ID = jsonObject.getString("CHECK_ID");
                            //数据来源系统
                            String ORIGIN_APP = "01";

                            //调用资金计划的占用接口
                            String xmlPlanMonthAmountQuery = XmlConmon.getXmlPlanMonthAmountQuery(ENTITY_ID, ENTITY_NAME, SUBJECT_ID, SUBJECT_NAME, FISCAL_MONTH, PROCESS_FLAG, DOCUMENT_NO, AMOUNT, REMARK, CHECK_ID, ORIGIN_APP);
                            String s = null;
                            try {
                                log.info("占用资金计划传递参数"+xmlPlanMonthAmountQuery);
                                s = WebServiceUtils.soapPost(urlCommon.urlPlanMonthAmountQuery(), xmlPlanMonthAmountQuery);
                                JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
                                log.info("占用资金计划返回参数"+xmlTranJsonObject.toString());
                                if ("1".equals(xmlTranJsonObject.getString("flag"))) {
                                    result.put("Message",xmlTranJsonObject.getString("mess"));
                                    entry.getValue();
                                    String occupy_id = SqlCommon.getSqlInsertCpReturn(entry.getValue().toString(), xmlTranJsonObject.getString("occupy_id"), flag);
                                    rktrtSqlUtils.insertCapitalPlanSql(occupy_id);
                                    return result;
                                } else {
                                    result.put("result", false);
                                    result.put("Message", xmlTranJsonObject.getString("mess"));
                                    result.put("Code", 0);
                                    result.put("value", "");
                                    return result;
                                }
                            } catch (IOException e) {
                                log.error("WebServiceUtils 调用接口失败");
                                e.printStackTrace();
                                throw new RuntimeException("调用接口失败 转换异常");
                            } catch (Exception e) {
                                log.error("ReadXmlUtils 转换异常");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }else{
            result.put("result", true);
            result.put("Message", "提交成功");
            result.put("Code", 1);
            result.put("value", "");
            return result;
        }

        result.put("result", false);
        result.put("Message", "提交失败");
        result.put("Code", 0);
        result.put("value", "");
        return result;
        // return null;
    }
    public Map<String, Object> retract(Map<String, Object> paraMap) {
        log.debug("backBeforeRS");
        Boolean flag=true;
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "撤回成功");
        result.put("Code", 1);
        result.put("value", "");
        if ((!"RS".equals(paraMap.get("funcSource"))) && (!"SSP".equals(paraMap.get("funcSource")))) {

            for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
                log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
//            HashMap stringToMap = (HashMap) entry.getValue();
                if ("billId".equals(entry.getKey())) {
                    //查询系统中的这个单据
                    JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlUniversalill(entry.getValue().toString()));
                    if (jsonArray.size() < 1) {
                        jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlBaoZhang(entry.getValue().toString()));
                        flag=false;
                    }
                    int size = jsonArray.size();
                    if (jsonArray.size() >= 1) {
                        for (int i = 0; i < size; i++) {
                            //拿到报账单中要传递的信息
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误
                            if (!jsonObject.getString("FISCAL_MONTH").equals(jsonObject.getString("BIZDATE"))) {
                                result.put("result", false);
                                result.put("Message", "填报时间错误 资金计划选择不正确");
                                result.put("Code", 0);
                                result.put("value", "");
                                log.debug("submit");
                                log.error("判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误");
                                return result;
                            }
                            //公司代码
                            String ENTITY_ID = jsonObject.getString("ENTITY_ID");
                            //公司名称
                            String ENTITY_NAME = jsonObject.getString("ENTITY_NAME");
                            //计划项目编码
                            String SUBJECT_ID = jsonObject.getString("SUBJECT_ID");
                            //计划项目名称
                            String SUBJECT_NAME = jsonObject.getString("SUBJECT_NAME");
                            //年月 jsonObject.getString("FISCAL_MONTH");
                            String FISCAL_MONTH = jsonObject.getString("FISCAL_MONTH");
                            //占用/释放标识
                            String PROCESS_FLAG = "1";
//                    String FISCAL_MONTH= jsonObject.getString("ROBXDJ_RQ");

                            //报账单号
                            String DOCUMENT_NO = jsonObject.getString("DOCUMENT_NO");
                            //实付金额
                            // String AMOUNT = jsonObject.getString("AMOUNT");
                            String ISNEED = jsonObject.getString("ISNEED");
                            //当1 时候假设不需要资金计划控制那么 就是全部 金额
                            String AMOUNT = "";
                            if ("不控制".equals(ISNEED)) {
                                AMOUNT = jsonObject.getString("AMOUNT");
                            } else {
                                //实付金额
                                AMOUNT = jsonObject.getString("AMOUNT");
                                BigDecimal bigDecimal = new BigDecimal(AMOUNT);
                                //当金额大于三十w时候 ze取30
                                if (bigDecimal.compareTo(new BigDecimal(fjfsExCommon.getPjqsje())) == 1) {
                                    BigDecimal multiply = bigDecimal.multiply(new BigDecimal(fjfsExCommon.getXhzfbl()));
                                    bigDecimal = multiply;
                                    AMOUNT = bigDecimal.toString();
                                }
                            }
                            //备注
                            String REMARK = jsonObject.getString("REMARK");
                            //数据行主键
                            String CHECK_ID = jsonObject.getString("CHECK_ID");
                            //数据来源系统
                            String ORIGIN_APP = "01";

                            //调用资金计划的占用接口
                            String xmlPlanMonthAmountQuery = XmlConmon.getXmlPlanMonthAmountQuery(ENTITY_ID, ENTITY_NAME, SUBJECT_ID, SUBJECT_NAME, FISCAL_MONTH, PROCESS_FLAG, DOCUMENT_NO, AMOUNT, REMARK, CHECK_ID, ORIGIN_APP);
                            String s = null;
                            try {
                                s = WebServiceUtils.soapPost(urlCommon.urlPlanMonthAmountQuery(), xmlPlanMonthAmountQuery);
                                JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
                                log.error(xmlTranJsonObject.toString());
                                if ("1".equals(xmlTranJsonObject.getString("flag"))) {
                                    result.put("Message", xmlTranJsonObject.getString("mess"));
                                    entry.getValue();
                                    String occupy_id = SqlCommon.getSqlInsertCpReturn(entry.getValue().toString(), "", flag);
                                    rktrtSqlUtils.insertCapitalPlanSql(occupy_id);
                                    return result;
                                } else {
                                    result.put("result", false);
                                    result.put("Message", xmlTranJsonObject.getString("mess"));
                                    result.put("Code", 0);
                                    result.put("value", "");
                                    return result;
                                }
                            } catch (IOException e) {
                                log.error("WebServiceUtils 调用接口失败"+e);
                                e.printStackTrace();
                                result.put("result", false);
                                result.put("Message", e);
                                result.put("Code", 0);
                                result.put("value", "");
                                return result;

                            } catch (Exception e) {
                                log.error("ReadXmlUtils 转换异常");
                                e.printStackTrace();
                                result.put("result", false);
                                result.put("Message", e);
                                result.put("Code", 0);
                                result.put("value", "");
                                return result;
                            }
                        }
                    }
                }
            }
        }else {
            result.put("result", true);
            result.put("Message", "撤回成功");
            result.put("Code", 1);
            result.put("value", "");
            return result;

        }
        result.put("result", false);
        result.put("Message", "撤回失败");
        result.put("Code", 0);
        result.put("value", "");
        return result;
    }

    //编辑 界面撤回前 retract  backBeforeRS
    @Override
    public Map<String, Object> retractAfter(Map<String, Object> paraMap) {
        log.debug("backBeforeRS");
        Boolean flag=true;
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "撤回成功");
        result.put("Code", 1);
        result.put("value", "");
        if (("RS".equals(paraMap.get("funcSource")) || "SSP".equals(paraMap.get("funcSource")))) {

            for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
                log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
//            HashMap stringToMap = (HashMap) entry.getValue();
                if ("billId".equals(entry.getKey())) {
                    //查询系统中的这个单据
                    JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlUniversalill(entry.getValue().toString()));
                    if (jsonArray.size() < 1) {
                        jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlBaoZhang(entry.getValue().toString()));
                        flag=false;
                    }
                    int size = jsonArray.size();
                    if (jsonArray.size() >= 1) {
                        for (int i = 0; i < size; i++) {
                            //拿到报账单中要传递的信息
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误
                            if (!jsonObject.getString("FISCAL_MONTH").equals(jsonObject.getString("BIZDATE"))) {
                                result.put("result", false);
                                result.put("Message", "填报时间错误 资金计划选择不正确");
                                result.put("Code", 0);
                                result.put("value", "");
                                log.debug("submit");
                                log.error("判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误");
                                return result;
                            }
                            //公司代码
                            String ENTITY_ID = jsonObject.getString("ENTITY_ID");
                            //公司名称
                            String ENTITY_NAME = jsonObject.getString("ENTITY_NAME");
                            //计划项目编码
                            String SUBJECT_ID = jsonObject.getString("SUBJECT_ID");
                            //计划项目名称
                            String SUBJECT_NAME = jsonObject.getString("SUBJECT_NAME");
                            //年月 jsonObject.getString("FISCAL_MONTH");
                            String FISCAL_MONTH = jsonObject.getString("FISCAL_MONTH");
                            //占用/释放标识
                            String PROCESS_FLAG = "1";
//                    String FISCAL_MONTH= jsonObject.getString("ROBXDJ_RQ");

                            //报账单号
                            String DOCUMENT_NO = jsonObject.getString("DOCUMENT_NO");
                            //实付金额
                            // String AMOUNT = jsonObject.getString("AMOUNT");
                            String ISNEED = jsonObject.getString("ISNEED");
                            //当1 时候假设不需要资金计划控制那么 就是全部 金额
                            String AMOUNT = "";
                            if ("不控制".equals(ISNEED)) {
                                AMOUNT = jsonObject.getString("AMOUNT");
                            } else {
                                //实付金额
                                AMOUNT = jsonObject.getString("AMOUNT");
                                BigDecimal bigDecimal = new BigDecimal(AMOUNT);
                                //当金额大于三十w时候 ze取30
                                if (bigDecimal.compareTo(new BigDecimal(fjfsExCommon.getPjqsje())) == 1) {
                                    BigDecimal multiply = bigDecimal.multiply(new BigDecimal(fjfsExCommon.getXhzfbl()));
                                    bigDecimal = multiply;
                                    AMOUNT = bigDecimal.toString();
                                }
                            }
                            //备注
                            String REMARK = jsonObject.getString("REMARK");
                            //数据行主键
                            String CHECK_ID = jsonObject.getString("CHECK_ID");
                            //数据来源系统
                            String ORIGIN_APP = "01";

                            //调用资金计划的释放接口

                            String xmlPlanMonthAmountQuery = XmlConmon.getXmlPlanMonthAmountQuery(ENTITY_ID, ENTITY_NAME, SUBJECT_ID, SUBJECT_NAME, FISCAL_MONTH, PROCESS_FLAG, DOCUMENT_NO, AMOUNT, REMARK, CHECK_ID, ORIGIN_APP);
                            log.info("资金计划撤回接口发送报文"+xmlPlanMonthAmountQuery);
                            String s = null;
                            try {
                                s = WebServiceUtils.soapPost(urlCommon.urlPlanMonthAmountQuery(), xmlPlanMonthAmountQuery);
                                JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
                                log.info("资金计划撤回返回接口报文"+xmlTranJsonObject.toString());
                                if ("1".equals(xmlTranJsonObject.getString("flag"))) {
                                    result.put("Message", xmlTranJsonObject.getString("mess"));
                                    entry.getValue();
                                    String occupy_id = SqlCommon.getSqlInsertCpReturn(entry.getValue().toString(), "", flag);
                                    rktrtSqlUtils.insertCapitalPlanSql(occupy_id);
                                    return result;
                                } else {
                                    result.put("result", false);
                                    result.put("Message", xmlTranJsonObject.getString("mess"));
                                    result.put("Code", 0);
                                    result.put("value", "");
                                    return result;
                                }
                            } catch (IOException e) {
                                log.error("WebServiceUtils 调用接口失败"+e);
                                e.printStackTrace();
                                result.put("result", false);
                                result.put("Message", e);
                                result.put("Code", 0);
                                result.put("value", "");
                                return result;

                            } catch (Exception e) {
                                log.error("ReadXmlUtils 转换异常");
                                e.printStackTrace();
                                result.put("result", false);
                                result.put("Message", e);
                                result.put("Code", 0);
                                result.put("value", "");
                                return result;
                            }
                        }
                    }
                }
            }
        }else {
            result.put("result", true);
            result.put("Message", "撤回成功");
            result.put("Code", 1);
            result.put("value", "");
            return result;

        }
        result.put("result", false);
        result.put("Message", "撤回失败");
        result.put("Code", 0);
        result.put("value", "");
        return result;
    }

    @Override
    public Map<String, Object> afterTriggerNode(Map<String, Object> paraMap) {
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "提交成功");
        result.put("Code", 1);
        result.put("value", "");
        log.debug("submit");
        for(Map.Entry<String, Object> entry: paraMap.entrySet()) {
            log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());

        }

        return result;
    }
    //列表界面提交
    @Override
    public Map<String, Object> submit(Map<String, Object> paraMap) {
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "提交成功");
        result.put("Code", 1);
        result.put("value", "");
        log.debug("submit");
        for(Map.Entry<String, Object> entry: paraMap.entrySet()) {
            log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
//            HashMap stringToMap = (HashMap) entry.getValue();
//            if (stringToMap.get("BILLID") != null) {
            //查询系统中的这个单据
            JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlUniversalill(entry.getValue().toString()));
            if(jsonArray.size()<1){
                jsonArray=  rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlBaoZhang(entry.getValue().toString()));
            }
            int size = jsonArray.size();
            if (jsonArray.size() >= 1) {
                for (int i = 0; i < size; i++) {
                    //拿到报账单中要传递的信息
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误
                    if (!jsonObject.getString("FISCAL_MONTH").equals(jsonObject.getString("BIZDATE"))){
                        result.put("result", false);
                        result.put("Message", "填报时间错误 资金计划选择不正确");
                        result.put("Code", 0);
                        result.put("value", "");
                        log.debug("submit");
                        log.error("判断系统时间和填报时间是否相等 若是不等 则提示资金计划占用失败 填报时间错误 资金计划期间错误");
                        return result;
                    }
                    //公司代码
                    String ENTITY_ID = jsonObject.getString("ENTITY_ID");
                    //公司名称
                    String ENTITY_NAME = jsonObject.getString("ENTITY_NAME");
                    //计划项目编码
                    String SUBJECT_ID = jsonObject.getString("SUBJECT_ID");
                    //计划项目名称
                    String SUBJECT_NAME = jsonObject.getString("SUBJECT_NAME");
                    //年月
                    String FISCAL_MONTH = jsonObject.getString("FISCAL_MONTH");
                    //占用/释放标识
                    String PROCESS_FLAG = "0";
//                    String FISCAL_MONTH= jsonObject.getString("ROBXDJ_RQ");

                    //报账单号
                    String DOCUMENT_NO = jsonObject.getString("DOCUMENT_NO");
                    //实付金额
                    String AMOUNT = jsonObject.getString("AMOUNT");
                    //备注
                    String REMARK = jsonObject.getString("REMARK");
                    //数据行主键
                    String CHECK_ID = jsonObject.getString("CHECK_ID");
                    //数据来源系统
                    String ORIGIN_APP = "01";
                    //调用资金计划的占用接口
                    String xmlPlanMonthAmountQuery = XmlConmon.getXmlPlanMonthAmountQuery(ENTITY_ID, ENTITY_NAME, SUBJECT_ID, SUBJECT_NAME, FISCAL_MONTH, PROCESS_FLAG, DOCUMENT_NO, AMOUNT, REMARK, CHECK_ID, ORIGIN_APP);
                    String s = null;
                    try {
                        s = WebServiceUtils.soapPost(urlCommon.urlPlanMonthAmountQuery(), xmlPlanMonthAmountQuery);
                        JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
                        log.error(xmlTranJsonObject.toString());
                        if ("1".equals(xmlTranJsonObject.getString("flag"))) {
                            result.put("Message",xmlTranJsonObject.getString("mess"));
                            return result;
                        } else {
                            result.put("result", false);
                            result.put("Message", xmlTranJsonObject.getString("mess"));
                            result.put("Code", 0);
                            result.put("value", "");
                            return result;
                        }
                    } catch (IOException e) {
                        log.error("WebServiceUtils 调用接口失败");
                        e.printStackTrace();
                        throw new RuntimeException("调用接口失败 转换异常");
                    } catch (Exception e) {
                        log.error("ReadXmlUtils 转换异常");
                        e.printStackTrace();
                    }
                }
            }
        }
//        }
        result.put("result", false);
        result.put("Message", "提交失败");
        result.put("Code", 0);
        result.put("value", "");
        return result;
    }
    //提交之后 撤回操作
    @Override
    public Map<String, Object> backBeforeRS(Map<String, Object> paraMap) {
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        log.debug("retract");
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "撤回成功");
        result.put("Code", 0);
        result.put("value", "");
        if (("RS".equals(paraMap.get("funcSource")) || "SSP".equals(paraMap.get("funcSource")))) {
            for(Map.Entry<String, Object> entry: paraMap.entrySet()) {
                log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
                //查询系统中的这个单据
                //通用报账单
                JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlUniversalill(entry.getValue().toString()));
                if (jsonArray.size()<1){
                    jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlBaoZhang(entry.getValue().toString()));
                }
                int size = jsonArray.size();
                if (jsonArray.size()>=1){
                    for (int i=0;i<size;i++){
                        //拿到报账单中要传递的信息
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //公司代码
                        String ENTITY_ID= jsonObject.getString("ENTITY_ID");
                        //公司名称
                        String ENTITY_NAME= jsonObject.getString("ENTITY_NAME");
                        //计划项目编码
                        String SUBJECT_ID= jsonObject.getString("SUBJECT_ID");
                        //计划项目名称
                        String SUBJECT_NAME=jsonObject.getString("SUBJECT_NAME");
                        //年月
                        String FISCAL_MONTH=jsonObject.getString("FISCAL_MONTH");
                        //占用/释放标识
                        String PROCESS_FLAG= "1";
//                    String FISCAL_MONTH= jsonObject.getString("ROBXDJ_RQ");

                        //报账单号
                        String DOCUMENT_NO=jsonObject.getString("DOCUMENT_NO");
                        //实付金额
                        String AMOUNT= jsonObject.getString("AMOUNT");
                        //备注
                        String REMARK=jsonObject.getString("REMARK");
                        //数据行主键
                        String CHECK_ID=jsonObject.getString("CHECK_ID");
                        //数据来源系统
                        String ORIGIN_APP ="01";
                        //调用资金计划的占用接口
                        String xmlPlanMonthAmountQuery = XmlConmon.getXmlPlanMonthAmountQuery(ENTITY_ID, ENTITY_NAME, SUBJECT_ID, SUBJECT_NAME, FISCAL_MONTH, PROCESS_FLAG, DOCUMENT_NO, AMOUNT, REMARK, CHECK_ID, ORIGIN_APP);
                        String s = null;
                        try {
                            s = WebServiceUtils.soapPost(urlCommon.urlPlanMonthAmountQuery(),xmlPlanMonthAmountQuery);
                            JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
                            log.error(xmlTranJsonObject.toString());
                            if ("1".equals(xmlTranJsonObject.getString("flag"))){
                                result.put("Message",xmlTranJsonObject.getString("mess"));
                                return result;
                            }else{
                                result.put("result", false);
                                result.put("Message",xmlTranJsonObject.getString("mess"));
                                result.put("Code", 0);
                                result.put("value", "");
                                return result;
                            }
                        } catch (IOException e) {
                            log.error("WebServiceUtils 调用接口失败");
                            e.printStackTrace();
                            throw  new RuntimeException("调用接口失败 转换异常");
                        } catch (Exception e) {
                            log.error("ReadXmlUtils 转换异常");
                            e.printStackTrace();
                            throw  new RuntimeException("ReadXmlUtils 转换异常");
                        }
                    }
                }
            }
        }else{
            result.put("result", true);
            result.put("Message", "撤回成功");
            result.put("Code", 1);
            result.put("value", "");
            return result;
        }
        result.put("result", false);
        result.put("Message", "撤回失败");
        result.put("Code", 0);
        result.put("value", "");
        return result;
    }
    //单据驳回操作
    @Override
    public Map<String, Object> approvebackAfter(Map<String, Object> paraMap) {
        log.info("approveback");
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "");
        result.put("Code", 1);
        result.put("value", "");
        HashMap<String,String> billCommonEntity =(HashMap)paraMap.get("billCommonEntity");
        if ("制单".equals(billCommonEntity.get("dqhjmcssp"))&&"驳回".equals(billCommonEntity.get("ztsm"))) {
            return this.retract(paraMap);
        }else {
                return result;
        }

    }

    @Override
    public Map<String, Object> complete(Map<String, Object> paraMap) {
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        log.debug("complete");
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "");
        result.put("Code", 1);
        result.put("value", "");
        for(Map.Entry<String, Object> entry: paraMap.entrySet()) {

        }

        return result;
    }
    //审核完成需要完成的扩展
    @Override
    public Map<String, Object> approvepassAfter(Map<String, Object> paraMap) {
        UrlCommon urlCommon = new UrlCommon(fjfsExCommon);
        log.debug("approvepass");
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("Message", "审核通过");
        result.put("Code", 1);
        result.put("value", "");

        HashMap stringToMap = (HashMap) paraMap.get("contextParam");
        if ("财务复核".equals(stringToMap.get("DQHJMC")))
        //if (("RS".equals(paraMap.get("funcSource")) || "SSP".equals(paraMap.get("funcSource"))))
        {

            //for (Map.Entry<String, Object> entry : paraMap.entrySet())
            if (stringToMap.get("BILLID")!=null)
            //if (billId != null)
            {
                String billId=(String) stringToMap.get("BILLID");
                log.error("单据ID为"+billId);
                //log.debug("key:123456789" + paraMap.getKey() + ", value:" + paraMap.getValue());
//            Map.Entry<String,Object> mapEntry=(Map.Entry)entry.getValue();
                //HashMap stringToMap = (HashMap) entry.getValue();

                // if (stringToMap.get("BILLID") != null) {
//                    if ("billId".equals(entry.getKey())) {
                //log.debug("key:" + entry.getKey() + ", value:" + entry.getValue());
                //JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlView_UniversaBillPay(stringToMap.get("BILLID").toString()));
                JSONArray jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlView_UniversaBillPay(billId.toString()));
                if (jsonArray.size() < 1) {
                    //jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlView_ReceiptBill(stringToMap.get("BILLID").toString()));
                    jsonArray = rktrtSqlUtils.selectResultSroTra(SqlCommon.getSqlView_ReceiptBill(billId.toString()));
                }
                log.error("数据库查询结果"+jsonArray.toString());
                int size = jsonArray.size();
                if (jsonArray.size() >= 1) {
                    StringBuffer sbf = new StringBuffer();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sbf.append(XmlConmon.getPayBillEntryXml(jsonObject.getString("REC_COMPANY_CODE"),jsonObject.getString("REC_COMPANY_NAME"),jsonObject.getString("REC_ACCOUNT_CODE"), jsonObject.getString("REC_ACCOUNT_NAME"),
                                jsonObject.getString("REC_HBANK_CODE"), jsonObject.getString("REC_HBANK_NAME"),
                                jsonObject.getString("AMOUNT"), jsonObject.getString("PURPOSE"),
                                jsonObject.getString("PUBLIC_FLAG"), jsonObject.getString("CHECK_ID"),jsonObject.getString("OCCUPY_ID")
                        ));
                    }
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String PayBillXml = XmlConmon.getPayBillXml(
                            jsonObject.getString("PAY_COMPANY_CODE"),
                            jsonObject.getString("PAY_COMPANY_NAME"),
                            jsonObject.getString("PAY_ACCOUNT_CODE"),
                            jsonObject.getString("PAY_ACCOUNT_NAME"),
                            jsonObject.getString("DOCUMENT_NO"),
                            jsonObject.getString("PAYMENT_MODE"),
                            jsonObject.getString("SUBMIT_NAME"),
                            jsonObject.getString("SUBMIT_DEPT"),
                            jsonObject.getString("ORIGIN_APP"),
                            jsonObject.getString("DOCUMENT_ID"),
                            sbf
                    );
                    log.error("发送报文"+PayBillXml);
                    try {
                        String s = WebServiceUtils.soapPost(urlCommon.urlpushBillAP(), PayBillXml);
                        log.error("是否发送成功"+s);
                        JSONObject xmlTranJsonObject = ReadXmlUtils.getXmlTranJsonObject(s);
                        if ("1".equals(xmlTranJsonObject.getString("flag"))) {
                            result.put("Message", xmlTranJsonObject.getString("message") + "成功");
                            log.error("审核成功"+xmlTranJsonObject.getString("flag")+xmlTranJsonObject.getString("message")+result);
                            return result;
                        } else {
                            StringBuffer errormessage = new StringBuffer();
                            //当返回失败时候需要根据cheak_id 在支付明细中反写上错误信息

                            JSONArray rd = xmlTranJsonObject.getJSONArray("RD");
                            if (rd!=null){
                            for (int i = 0; i < rd.size(); i++) {
                                JSONObject jsonObject1 = rd.getJSONObject(i);
                                String check_id = jsonObject1.getString("CHECK_ID");
                                String message = jsonObject1.getString("message");
                                errormessage.append(message);
                                log.error("推送失败"+ errormessage.append(message));
                                //rktrtSqlUtils.insertCapitalPlanSql(SqlCommon.getSqlIstFailt(check_id, message));
                            }
                            }
                            result.put("result", false);
                            if (errormessage!=null) {
                                result.put("Message", xmlTranJsonObject.getString("message") + errormessage.toString());
                                log.error("推送失败"+xmlTranJsonObject.getString("message")+errormessage.toString());
                            }else {
                                result.put("Message", xmlTranJsonObject.getString("message"));
                                log.error("推送失败"+xmlTranJsonObject.getString("message"));
                            }
                            result.put("Code", 0);
                            result.put("value", "");
                            return result;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("调用urlpushBillAP 接口失败");
                        //throw new RuntimeException("approvepass审核通过失败 原因：" + e);
                        result.put("result", false);
                        result.put("Message", "审核失败" + e);
                        result.put("Code", 0);
                        result.put("value", "");
                        return result;
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("approvepass审核失败调用格式转化失败");
                        //throw new RuntimeException("approvepass审核通过失败原因：" + e);
                        result.put("result", false);
                        result.put("Message", "审核失败" + e);
                        result.put("Code", 0);
                        result.put("value", "");
                        return result;
                    }

                    // }
                }
            }
        }else {
            log.error("失败");
            result.put("result", true);
            result.put("Message", "审核通过");
            result.put("Code", 1);
            result.put("value", "");
            log.error(result.toString());
            return result;
        }
        result.put("result", false);
        result.put("Message", "审核失败");
        result.put("Code", 0);
        result.put("value", "");
        return result;

    }}