package com.inspur.zzy.fjgx.fsex.core.common;

import org.springframework.beans.factory.annotation.Autowired;


public class UrlCommon {
    private FJFSExCommon common;
    public UrlCommon(FJFSExCommon fjfsExCommon) {
        this.common = fjfsExCommon;
    }
    //资金查询接口url cs
    public String urlQuerycapitalPlan(){
        return common.getAddr()+"PlanMonthQueryService?wsdl";
    }

    //资金占用接口URL cs

    public String urlPlanMonthAmountQuery(){
        return common.getAddr()+"REPlanMonthAmountService?wsdl";
    }


    //付款单接口 cs
    public String urlpushBillAP(){
        return common.getAddr()+"ErpPayoutReceiveService?wsdl";
    }

}
