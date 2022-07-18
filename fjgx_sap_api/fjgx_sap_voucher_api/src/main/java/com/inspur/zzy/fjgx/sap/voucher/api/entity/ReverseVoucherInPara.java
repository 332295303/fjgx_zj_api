package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReverseVoucherInPara {
    @JsonProperty("REQUEST")
    private ReverseRequest request;
}
