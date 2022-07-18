package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReverseRequest {
    @JsonProperty("API_ATTRS")
    private ApiAttrs apiAttrs;

    @JsonProperty("REQUEST_DATA")
    private ReverseRequsetData requsetData;
}
