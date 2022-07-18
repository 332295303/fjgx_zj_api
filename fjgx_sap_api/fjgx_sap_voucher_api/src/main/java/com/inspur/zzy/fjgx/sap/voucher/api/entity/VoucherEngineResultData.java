package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherEngineResultData {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("ZKEY")
    private String zkey;
    @JsonProperty("SUCCESS")
    private String success;
    @JsonProperty("MSG")
    private String msg;
    @JsonProperty("PASSPROCESS")
    private String passprocess;
    @JsonProperty("ERPCODE")
    private String erpcode;
}
