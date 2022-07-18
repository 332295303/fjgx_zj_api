package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutdaBody {
    @JsonProperty("BUKRS")
    private String BUKRS;

    @JsonProperty("GJAHR")
    private String GJAHR;

    @JsonProperty("BELNR")
    private String BELNR;
}

