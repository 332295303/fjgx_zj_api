package com.inspur.zzy.fjgx.fsex.core.common;

public class SqlCommon {
    /*
      当付款单传输失败时候反写支付信息
     */
    public  static String getSqlIstFailt(String cheak_id,String failureInfo){
        String sql ="update ROBXFK set ROBXFK_XM1='"+failureInfo+"' where ROBXFK_fknm='"+cheak_id+"' ";
        return sql;
    }
    /*
        @date：202200717
        @name:hcy
        @message：资金占用接口回写
     */

     public static String getSqlInsertCpReturn(String billid,String capitialId,Boolean flag){
         if (flag){
            return "update ROBXDJ set ROBXDJ_XM18='"+capitialId+"' where ROBXDJ_NM='"+billid+"'";
         }
         else {
              return "update FSROTRAVELBILL set ROBXDJ_XM18='"+capitialId+"' where ROBXDJ_NM='"+billid+"'";
         }
     }



    /*
                create or replace view user_views as
            (
            select
            sr.ROBXDJ_BZDWBH as ENTITY_ID,
            sr.ROBXDJ_DWMC as ENTITY_NAME,
            zj.subject_id as SUBJECT_ID,
            zj.subject_name as SUBJECT_NAME,
            TO_CHAR(SYSDATE,'YYYYMM') as FISCAL_MONTH,
            0 as PROCESS_FLAG,
            sr.ROBXDJ_BH as DOCUMENT_NO,
            sr.ROBXDJ_SFJE as AMOUNT,
            sr.ROBXDJ_ZY as REMARK,
            sr.ROBXDJ_NM as CHECK_ID,
            01 as ORIGIN_APP,
            sr.ROBXDJ_NM BZDID,
            TO_CHAR(sr.ROBXDJ_RQ ,'YYYYMM') as BIZDATE,
            sr.ROBXDJ_GXM2 as ISNEED
            from
            ROBXDJ   sr
            inner join FJZJJH  zj on sr.ROBXDJ_NM15=zj.id
            );
     */
    //通用报销单 付款单Sql信息 视图
    public static String getSqlView_UniversaBillPay(String reimbursementparam){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select * from view_payuniversabillpay where ROBXDJ_NM='"+reimbursementparam+"'");
        return sbf.toString();
    }
    /*
            create or replace view view_payuniversabillpay as
        (
            select
            unit.summary as PAY_COMPANY_CODE ,--付款公司编码
            bxz.ROBXDJ_DWMC as PAY_COMPANY_NAME,--付款公司名称
            paytail.ROBXFK_FKZH as PAY_ACCOUNT_CODE,--付款账号
            paytail.ROBXFK_FKZHM as PAY_ACCOUNT_NAME,--付款户名
            paytail.ROBXFK_DFBH1 as REC_COMPANY_CODE,--供应商编码
            paytail.ROBXFK_DFMC as REC_COMPANY_NAME,--供应商名称
             paytail.ROBXFK_DFZH as REC_ACCOUNT_CODE,--收款账号
            paytail.ROBXFK_DFZHM as REC_ACCOUNT_NAME,--收款户名
            yh.BANKIDENTIFIER as REC_HBANK_CODE,--收款开户行人行号
            paytail.ROBXFK_DFMC as REC_HBANK_NAME,--收款开户行人行名
            paytail.ROBXFK_FKJE as AMOUNT,--交易金额
            bxz.ROBXDJ_ZY as PURPOSE,--事由
            '1' as PUBLIC_FLAG,--对公标志  paytail.ROBXFK_DGDS
            bxz.ROBXDJ_BH as DOCUMENT_NO,--报账单号
            paytail.robxfk_fknm as CHECK_ID,--数据行主键
            paytail.robxfk_zffs as PAYMENT_MODE,--支付方式 01现汇
            bxz.ROBXDJ_YGNM as SUBMIT_NAME,--报账人名称
            bxz.ROBXDJ_BZBMMC as SUBMIT_DEPT,--报账人部门名称
            bxz.ROBXDJ_xm18 as OCCUPY_ID,--占用id
            01 as ORIGIN_APP, --数据来源系统
            bxz.robxdj_nm as ROBXDJ_NM,
            bxz.ROBXDJ_NM as DOCUMENT_ID
            FROM ROBXDJ bxz
            inner join ROBXFK paytail on bxz.ROBXDJ_NM=paytail.robxfk_bxnm
            inner join BFMASTERORGANIZATION unit on unit.id=bxz.ROBXDJ_BZDWID
            left join BFBank yh on yh.ID = paytail.ROBXFK_DFYHID

        )
            ;


     */

    //差旅费 付款单sql信息 视图  出现变化随时更改
    public static String getSqlView_ReceiptBill(String reimbursementparam){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select * from View_ReceiptBill where ROBXDJ_NM='"+reimbursementparam+"'");
        return sbf.toString();
    }
    /*

            create or replace view view_receiptbill as
            (
            select
            unit.summary as PAY_COMPANY_CODE ,--付款公司编码
            bxz.ROBXDJ_DWMC as PAY_COMPANY_NAME,--付款公司名称
            paytail.ROBXFK_FKZH as PAY_ACCOUNT_CODE,--付款账号
            paytail.ROBXFK_FKZHM as PAY_ACCOUNT_NAME,--付款户名
            paytail.ROBXFK_DFBH1 as REC_COMPANY_CODE,--供应商编码
            paytail.ROBXFK_DFMC as REC_COMPANY_NAME,--供应商名称
            paytail.ROBXFK_DFZH as REC_ACCOUNT_CODE,--收款账号
            paytail.ROBXFK_DFZHM as REC_ACCOUNT_NAME,--收款户名
            yh.BANKIDENTIFIER as REC_HBANK_CODE,--收款开户行人行号
            paytail.ROBXFK_DFMC as REC_HBANK_NAME,--收款开户行人行名
            paytail.ROBXFK_FKJE as AMOUNT,--交易金额
            bxz.ROBXDJ_ZY as PURPOSE,--事由
            '1' as PUBLIC_FLAG,--对公标志 paytail.ROBXFK_DGDS
            bxz.ROBXDJ_BH as DOCUMENT_NO,--报账单号
            paytail.robxfk_fknm as CHECK_ID,--数据行主键
            paytail.robxfk_zffs as PAYMENT_MODE,--支付方式 01现汇
            bxz.ROBXDJ_YGNM as SUBMIT_NAME,--报账人名称
            bxz.ROBXDJ_BZBMMC as SUBMIT_DEPT,--报账人部门名称
            01 as ORIGIN_APP, --数据来源系统
            bxz.ROBXDJ_xm18 as OCCUPY_ID,--占用id
            bxz.robxdj_nm as ROBXDJ_NM,
            bxz.ROBXDJ_NM as DOCUMENT_ID
            FROM FSROTRAVELBILL bxz
            inner join ROBXFK paytail on bxz.ROBXDJ_NM=paytail.robxfk_bxnm
            inner join BFMASTERORGANIZATION unit on unit.id=bxz.ROBXDJ_BZDWID
            left join BFBank yh on yh.ID = paytail.ROBXFK_DFYHID
            )
            ;

     */

    //通用报销单占用资金计划SQl
    public static String getSqlUniversalill(String param){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select * from View_OccupyCapital where BZDID='"+param+"'");
        return sbf.toString();
    }

    //返回报账单据信息
    public static String getSqlBaoZhang(String reimbursementparam){
        StringBuffer sbf = new StringBuffer();
        sbf.append("select ");//
        sbf.append("sr.ROBXDJ_BZDWBH as ENTITY_ID, ");//--公司编码
        sbf.append("sr.ROBXDJ_DWMC as ENTITY_NAME, ");//--公司名称
        sbf.append("zj.subject_id as SUBJECT_ID, ");//--计划项目编码
        sbf.append("zj.subject_name as SUBJECT_NAME, ");//--计划项目名称
        sbf.append("TO_CHAR(SYSDATE,'YYYYMM') as FISCAL_MONTH,  ");//--年月
        sbf.append("0 as PROCESS_FLAG, ");//--占用释放标识
        sbf.append("sr.ROBXDJ_BH as DOCUMENT_NO, ");//--报账单号
        sbf.append("sr.ROBXDJ_SFJE as AMOUNT, ");//--交易金额
        sbf.append("sr.ROBXDJ_ZY as REMARK, ");//---备注
        sbf.append("sr.ROBXDJ_NM as CHECK_ID, ");//--数据行主键
        sbf.append("01 as ORIGIN_APP, ");//--标识 01浪潮系统
        sbf.append("sr.ROBXDJ_NM BZDID,  ");
        sbf.append(" TO_CHAR(sr.ROBXDJ_RQ ,'YYYYMM') as BIZDATE ");
        sbf.append("from ");//
        sbf.append(" FSROTRAVELBILL   sr ");//
        sbf.append("inner join FJZJJH  zj on sr.ROBXDJ_XM16=zj.id ");//
        sbf.append("where sr.robxdj_nm='"+reimbursementparam+"'");
        return sbf.toString();
    }

    public static String getSqlPayBill(){
        return "";
    }
}
