package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherOutPara {
    @JsonProperty("RESPONSE")
    private ResponseBody responseBody;
}
