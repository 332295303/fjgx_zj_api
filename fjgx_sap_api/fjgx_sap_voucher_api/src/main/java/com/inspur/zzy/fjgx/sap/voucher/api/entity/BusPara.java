package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusPara {
    @JsonProperty("INTYP")
    private String INTYP;
    @JsonProperty("USERD")
    private String USERD;
    @JsonProperty("VERSN")
    private String VERSN;
    @JsonProperty("OSPLM")
    private String OSPLM;
    @JsonProperty("MODED")
    private String MODED;
}
