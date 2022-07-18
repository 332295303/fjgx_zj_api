package com.inspur.zzy.fjgx.sap.voucher.core.rpc;

import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import com.inspur.zzy.fjgx.common.core.service.FJHttpUtils;
import com.inspur.zzy.fjgx.common.core.service.FJMapUtils;
import com.inspur.zzy.fjgx.sap.voucher.api.common.VoucherConstants;
import com.inspur.zzy.fjgx.sap.voucher.api.entity.*;
import com.inspur.zzy.fjgx.sap.voucher.api.rpc.SAPVoucherRpcService;
import io.iec.edp.caf.boot.context.CAFContext;
import io.iec.edp.caf.common.JSONSerializer;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.util.CollectionUtils;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.ws.rs.NotSupportedException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class SAPVoucherRpcServiceImpl implements SAPVoucherRpcService {
    private FJConfiguationProperties fjConfiguationProperties;
    private EntityManager entityManager;

    public SAPVoucherRpcServiceImpl(FJConfiguationProperties configuationProperties) {
        this.fjConfiguationProperties = configuationProperties;
        this.entityManager = SpringBeanUtils.getBean(EntityManager.class);
    }

    @Override
    public String pushOrCancel(String map) {
        /**
         * 1.根据operation 判断 是凭证推送还是撤销
         * 2.根据判断结果走不同分支 调用sap接口
         **/
        log.error("map:" + map);
        Map params = JSONSerializer.deserialize(map,Map.class);
        VoucherEngineResult result = doSelect(params);
        String reStr = JSONSerializer.serialize(result);
        log.info(reStr);
        return reStr;
    }

    private VoucherEngineResult doSelect(Map params){
        String op = params.get("operation").toString();
        List<Map> data= (List<Map>) params.get("data");
        switch (op){
            case VoucherConstants.Ossi.PUSH:return doPush(data);
            case VoucherConstants.Ossi.CANCEL: return doCancel(data);
            default:throw new RuntimeException(String.format("未识别的操作:%s",op));
        }
    }

    private VoucherEngineResult doPush(List<Map> data){
        if(CollectionUtils.isEmpty(data)){
            throw new RuntimeException("没有需要推送的数据");
        }
        log.info("开始推送凭证,共{}条",Integer.valueOf(data.size()).toString());
        List res=new ArrayList();

        VoucherEngineResult voucherEngineResult = new VoucherEngineResult();
        List<VoucherEngineResultData> voucherEngineResultDataList = new ArrayList<>();

        String url = fjConfiguationProperties.getSap().get("addr");
        for (Map map : data) {
            //获取映射后的凭证数据
            String id = map.get("ID").toString();
            String zkey=map.get("ZKEY").toString();
            String strYear = map.get("YEAR").toString();
            Map mapping = (Map) map.get("ERPMap");


            VoucherEngineResultData voucherEngineResultData = new VoucherEngineResultData();
            try {
                VoucherInPara inPara = new VoucherInPara();
                Request request = new Request();
                request.setApiAttrs(getApiAtts());

                RequsetData requestData = new RequsetData();
                String intrp = "FI_AP_001"; //接口编号
                requestData.setBusPara(getBusPara(intrp));

                BusData busData = new BusData();
                BusDataHeader busDataHeader = new BusDataHeader();
                //凭证头字段赋值
                FJMapUtils.setFieldByMap(mapping, busDataHeader);
                log.debug("busDataHeader:{}", busDataHeader.toString());
                busData.setBusDataHeader(busDataHeader);
                //凭证分录赋值
                List<Map> items = (List<Map>) map.get("ParkingDocumentItem");
                List<BusDataItem> listBusDataItem = new ArrayList<>();
                for (Map item : items) {
                    //映射后的凭证分录
                    Map itemMapping = (Map) item.get("ERPMap");
                    BusDataItem busDataItem =new BusDataItem();
                    FJMapUtils.setFieldByMap(itemMapping, busDataItem);

                    log.debug("busDataItem:{}", busDataItem.toString());
                    listBusDataItem.add(busDataItem);
                }
                busData.setBusDataItemList(listBusDataItem);
                requestData.setBusData(busData);
                request.setRequsetData(requestData);
                inPara.setRequest(request);

                String json = JSONSerializer.serialize(inPara);
                log.info("请求数据：{}", json);
                String retJson;
                Map<String,String> info=new HashMap<>();

                retJson = FJHttpUtils.post(url, json, "application/json");
                log.info("返回数据：{}", retJson);

                VoucherOutPara voucherOutPara = JSONSerializer.deserialize(retJson, VoucherOutPara.class);
                ResponseBody responseBody = voucherOutPara.getResponseBody();
                String returnCode = responseBody.getReturnCode();
                String returnMsg = responseBody.getReturnDesc();
                ReturnData returnData = responseBody.getReturnData();

                if(returnCode.equals("S")) { //生成成功
                    Outda outda = new Outda();
                    outda = JSONSerializer.deserialize(returnData.getReturnData().getResda(), Outda.class);
                    OutdaBody outdaBody = outda.getOutdaBody();
                    voucherEngineResultData.setId(id);
                    voucherEngineResultData.setZkey(zkey);
                    voucherEngineResultData.setSuccess("1");
                    voucherEngineResultData.setMsg("推送成功");
                    voucherEngineResultData.setPassprocess("1");
                    voucherEngineResultData.setErpcode(outdaBody.getBELNR());
                } else { //失败
                    voucherEngineResultData.setId(id);
                    voucherEngineResultData.setZkey(zkey);
                    voucherEngineResultData.setSuccess("0");
                    voucherEngineResultData.setMsg(returnMsg);
                    voucherEngineResultData.setPassprocess("0");
                    voucherEngineResultData.setErpcode("");
                }
                voucherEngineResultDataList.add(voucherEngineResultData);

            } catch (Throwable e) {
                voucherEngineResultData.setId(id);
                voucherEngineResultData.setZkey(zkey);
                voucherEngineResultData.setSuccess("0");
                voucherEngineResultData.setMsg(e.getMessage());
                voucherEngineResultData.setPassprocess("0");
                voucherEngineResultData.setErpcode("");
                log.error("推送sap凭证接口异常：" + e);
                voucherEngineResultDataList.add(voucherEngineResultData);
                continue;
            }
        }

        voucherEngineResult.setAsyn(VoucherConstants.Ossi.SYNC);
        voucherEngineResult.setDataList(voucherEngineResultDataList);
        return voucherEngineResult;
    }

    private ApiAttrs getApiAtts() {
        ApiAttrs apiAttrs = new ApiAttrs();
        apiAttrs.setApiID("fj.app.erpmobil.zcreopenapi001");
        apiAttrs.setApiVersion("1.0.0");
        apiAttrs.setAppToken("2d99b6a8-b6d8-4d48-b3f2-fda0de34f648");
        apiAttrs.setDiviceID("E1443195-BC8E-415E-8325-77AC8308B60B");
        apiAttrs.setSign("111111111111111111111");
        apiAttrs.setTimeStamp(formatNowDate("yyyy-MM-dd HH:mm:ss:SSS"));
        apiAttrs.setAppSubID("1000000122CU");
        apiAttrs.setPartnerID("10000000");
        apiAttrs.setDiviceVersion("1.0");
        apiAttrs.setAppID("1000000122CU");
        apiAttrs.setOsVersion("1.0");
        apiAttrs.setAppVersion("2.1");
        return apiAttrs;
    }

    private BusPara getBusPara(String intrp) {
        BusPara busPara = new BusPara();
        busPara.setINTYP(intrp);
        busPara.setMODED("");
        busPara.setOSPLM("");
        busPara.setUSERD("");
        busPara.setVERSN("");
        return busPara;
    }

    /*
    * 格式化当前日期
    * */
    private String formatNowDate(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date nowDate = new Date();
        return format.format(nowDate);
    }

    private VoucherEngineResult doCancel(List<Map> data){
        List<VoucherEngineResultData> voucherEngineResultDataList = new ArrayList<>();
        String userId = CAFContext.current.getUserId();
        String userName = "";
        String url = "";
        try{
            userName = getUserName(userId);
            url = fjConfiguationProperties.getSap().get("addr");
            log.debug("userid:{}, name:{}, url:{}", userId, userName, url);
        }
        catch (Exception e) {
            log.error("error:{}", e.getMessage() );
        }

        for (Map map : data) {
            VoucherEngineResultData voucherEngineResultData = new VoucherEngineResultData();
            //map.forEach((key, value) -> log.debug("key= " + key + " and value= " + value));

            String id = map.get("ID").toString();
            String zkey=map.get("ZKEY").toString();
            String spCode = map.get("SPCODE").toString();
            try
            {
                String selSql="select XM4 YWLX, XM5 DJBH, XM8 GSBH, XM9 GZRQ, SPCODE PZH, YEAR PZND from ossiparkingdocument WHERE id = '" + id + "' and msgty='1'";
                log.debug("selsql:{}", selSql);
                Query query = this.entityManager.createNativeQuery(selSql).unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map<String, String>> resultList = query.getResultList();
                Map<String, String> result = resultList.get(0);
                log.debug("result:{}", result.toString());
                String ywlx = result.get("YWLX"),
                        gsbh = result.get("GSBH"),
                        djbh = result.get("DJBH"),
                        gzrq = result.get("GZRQ"),
                        pzh = result.get("PZH"),
                        pznd = result.get("PZND");

                ReverseVoucherInPara inPara = new ReverseVoucherInPara();
                ReverseRequest request = new ReverseRequest();
                request.setApiAttrs(getApiAtts());

                ReverseRequsetData requestData = new ReverseRequsetData();
                String intrp = "FI_AP_002"; //接口编号
                requestData.setBusPara(getBusPara(intrp));

                String dqyf = formatNowDate("yyyyMM");
                String gzyf = gzrq.substring(0, 6);
                String stgrd = ""; //冲销类型
                if(dqyf.compareTo(gzyf) == 0) {
                    stgrd = "03"; //当前期间内实际红字冲销
                } else {
                    stgrd = "04"; //以前期间内实际红字冲销
                }

                ReverseBusData busData = new ReverseBusData();
                busData.setBzlnr(djbh);
                busData.setBukrs(gsbh);
                busData.setBelnr(pzh);
                busData.setGjahr(pznd);
                busData.setStgrd(stgrd);
                busData.setBudat(gzrq);
                busData.setBznam(userName);
                busData.setZznam(userName);
                busData.setResv01("");
                busData.setResv02("");
                busData.setResv03("");
                busData.setResv04("");
                busData.setResv05("");
                requestData.setBusData(busData);

                request.setRequsetData(requestData);
                inPara.setRequest(request);

                String json = JSONSerializer.serialize(inPara);
                log.info("撤回凭证请求数据：{}", json);
                String retJson;
                Map<String,String> info=new HashMap<>();

                retJson = FJHttpUtils.post(url, json, "application/json");
                log.info("返回数据：{}", retJson);

                VoucherOutPara voucherOutPara = JSONSerializer.deserialize(retJson, VoucherOutPara.class);
                ResponseBody responseBody = voucherOutPara.getResponseBody();
                String returnCode = responseBody.getReturnCode();
                String returnMsg = responseBody.getReturnDesc();
                ReturnData returnData = responseBody.getReturnData();

                if(returnCode.equals("S")) { //撤回成功
                    voucherEngineResultData.setZkey(zkey);
                    voucherEngineResultData.setMsg("撤回成功");
                    voucherEngineResultData.setSuccess("1");
                    voucherEngineResultData.setId(id);
                    voucherEngineResultData.setPassprocess("1");
                } else { //失败
                    voucherEngineResultData.setId(id);
                    voucherEngineResultData.setZkey(zkey);
                    voucherEngineResultData.setSuccess("0");
                    voucherEngineResultData.setMsg(returnMsg);
                    voucherEngineResultData.setPassprocess("0");
                    voucherEngineResultData.setErpcode("");
                }

                voucherEngineResultDataList.add(voucherEngineResultData);
            }
            catch (Exception e) {
                voucherEngineResultData.setZkey(zkey);
                voucherEngineResultData.setMsg(e.getMessage());
                voucherEngineResultData.setSuccess("0");
                voucherEngineResultData.setId(id);
                voucherEngineResultData.setPassprocess("0");
                voucherEngineResultDataList.add(voucherEngineResultData);
            }
        }

        VoucherEngineResult voucherEngineResult = new VoucherEngineResult();
        voucherEngineResult.setAsyn(VoucherConstants.Ossi.SYNC);
        voucherEngineResult.setDataList(voucherEngineResultDataList);
        return voucherEngineResult;
    }

    private String getUserName(String userId){
        String sql="select name_chs as name from gspuser where id='" + userId + "'";
        Query query = this.entityManager.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Object>> results = query.getResultList();
        if(results.size()>0)
            return results.get(0).get("NAME").toString();
        return null;
    }

    @Override
    public Map<String, Object> afterGenerate(Map<String, Object> map) {
        log.debug("map:{}", map);
        List<Map<String,Object>> voucherList = (List<Map<String,Object>>) map.get("voucher");
        for(Map<String, Object> voucherMap: voucherList) {
            String gzrq = voucherMap.get("XM9").toString();
            String year = gzrq.substring(0, 4);
            String ywlxFyxm = voucherMap.get("XM4").toString();
            String ywlx = getFYXMTargetMapValue(year, "100009", "02", ywlxFyxm);
            voucherMap.put("XM4", ywlx);

            List<Map<String,Object>> voucherItemList = (List<Map<String,Object>>) voucherMap.get("ParkingDocumentItem");
            for(Map<String, Object> voucherItem: voucherItemList) {
                String yydmFyxm = Objects.nonNull(voucherItem.get("XM5"))? voucherItem.get("XM5").toString(): "";

                log.debug("yydmFyxm:{}", yydmFyxm);
                if(!StringUtils.isEmpty(yydmFyxm)) {
                    String yydm = getFYXMTargetMapValue(year, "100002", "01", yydmFyxm);
                    log.debug("yydm:{}", yydm);
                    voucherItem.put("XM5", yydm);
                }
            }
        }
        return map;
    }

    private String getFYXMTargetMapValue(String year, String ysdybh, String cusCate, String fyxmid)
    {
        String sql = "select yydm.cusitemcode cuscode \n" +
                "from FIAIPVALUEMAPPING ysdy\n" +
                "join FIAIPVMValueList ysgx on ysgx.parentid = ysdy.id\n" +
                "join bfexpensestype fyxm on ysgx.srccol01 = fyxm.id\n" +
                "join BFCustomItemCategory" + year + " cate on cate.code = '" + cusCate + "'\n" +
                "join BFCustomItem" + year + " yydm on yydm.id = ysgx.tarcol and yydm.cusitemcategory = cate.id\n" +
                "where ysdy.code = '" + ysdybh + "'\n" +
                "and fyxm.id = '" + fyxmid + "'";
        log.debug("sql:{}", sql);
        Query query = this.entityManager.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = query.getResultList();
        if(resultList.size() > 0) {
            return resultList.get(0).get("CUSCODE").toString();
        } else {
            return fyxmid; //找不到则返回原值
        }
    }
}
