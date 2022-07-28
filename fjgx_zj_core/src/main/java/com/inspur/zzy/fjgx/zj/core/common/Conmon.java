package com.inspur.zzy.fjgx.zj.core.common;

public class Conmon {
    //查询 浪潮的组织架构
    public static String getCapitalSql() {
        StringBuffer sbfSql = new StringBuffer();
        sbfSql.append("select ");
        sbfSql.append("unit.ID as CHECK_ID, ");//--数据行主键
        sbfSql.append("unit.name_chs as ENTITY_NAME, ");// --名称
        sbfSql.append("unit.PLAINTEXT1 as ENTITY_ID, ");// --公司编码
        sbfSql.append("01 as ORIGIN_APP, ");//--浪潮标识
        sbfSql.append("TO_CHAR(SYSDATE,'YYYYMM') as FISCAL_MONTH ");//--时间格式
        sbfSql.append("from ");
        sbfSql.append("bfadminorganization unit where trim(PLAINTEXT1) is not null");//--组织表
        return sbfSql.toString();
    }

    //查询要插入的资金计划是否存在
    public static String isExistCapitalPlanNotes(String ENTITY_ID,String SUBJECT_ID,String FISCAL_MONTH) {
        return "select * from FJZJJH where ENTITY_ID='"+ENTITY_ID+"' and SUBJECT_ID='"+SUBJECT_ID+"' and FISCAL_MONTH=to_date（'"+FISCAL_MONTH+"','YYYYMM'）";
    }

    //插入资金计划表数据
    public static String insertCapitalPlanSql(String ENTITY_ID, String ENTITY_NAME, String SUBJECT_ID, String SUBJECT_NAME,
                                              String FISCAL_MONTH, String AMOUNT, String REMARK) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into FJZJJH(");
        //版本
        stringBuffer.append("version,");
        //公司代码
        stringBuffer.append("ENTITY_ID,");



        //公司名称
        stringBuffer.append("ENTITY_NAME,");
        //计划项目编码
        stringBuffer.append("SUBJECT_ID,");
        //计划项目名称
        stringBuffer.append("SUBJECT_NAME,");
        //年月
        stringBuffer.append("FISCAL_MONTH,");
        //计划可用金额
        stringBuffer.append("AMOUNT,");
        //备注
        stringBuffer.append("REMARK) ");
        stringBuffer.append("values");
        stringBuffer.append("(to_date('" + FISCAL_MONTH + "','YYYYMM'),");
        stringBuffer.append("'" + ENTITY_ID + "',");
        stringBuffer.append("'" + ENTITY_NAME + "',");
        stringBuffer.append("'" + SUBJECT_ID + "',");
        stringBuffer.append("'" + SUBJECT_NAME + "',");
        stringBuffer.append("to_date('" + FISCAL_MONTH + "','YYYYMM'),");
        stringBuffer.append("'" + AMOUNT + "',");
        stringBuffer.append("'" + REMARK + "')");
        return stringBuffer.toString();
    }



}
