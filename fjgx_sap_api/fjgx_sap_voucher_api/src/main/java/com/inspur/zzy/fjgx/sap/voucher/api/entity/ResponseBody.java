package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBody {
    @JsonProperty("RETURN_CODE")
    private String returnCode;
    @JsonProperty("RETURN_STAMP")
    private String returnSTAMP;
    @JsonProperty("RETURN_DATA")
    private ReturnData returnData;
    @JsonProperty("RETURN_DESC")
    private String returnDesc;
}

