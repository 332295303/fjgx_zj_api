package com.inspur.zzy.fjgx.sap.voucher.api.entity;
import lombok.Data;

@Data
public class FSResult {
    /**
     * 返回结果 成功：true  失败:false
     */
    private boolean result;
    /**
     * 操作是否成功 0：失败  1：成功 2：提示
     */
    private int Code;
    /**
     * 操作失败时返回失败原因
     */
    private String Message;
    /**
     * 用于返回其他有用信息
     * 返回失败、确认信息
     */
    private Object value;
    /**
     * 数据源id
     */
    private Object datasourceID;

}
