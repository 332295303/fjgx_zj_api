package com.inspur.zzy.fjgx.zj.core.common;

import org.springframework.beans.factory.annotation.Autowired;

public class UrlCommon {
    private FJZJCommon common;
    public UrlCommon(FJZJCommon fjzjCommon) {
        this.common = fjzjCommon;
    }

    //资金查询接口url cs
    public  String urlQuerycapitalPlan(){
        return  common.getAddr()+"PlanMonthQueryService?wsdl";
    }


    //资金占用接口URL cs

    public  String urlPlanMonthAmountQuery(){
        return common.getAddr()+"REPlanMonthAmountService?wsdl";
    }
}
