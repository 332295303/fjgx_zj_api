package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAttrs {
    @JsonProperty("App_Token")
    private String appToken;
    @JsonProperty("Divice_ID")
    private String diviceID;
    @JsonProperty("Sign")
    private String sign;
    @JsonProperty("OS_Version")
    private String osVersion;
    @JsonProperty("Api_ID")
    private String apiID;
    @JsonProperty("App_ID")
    private String appID;
    @JsonProperty("App_Sub_ID")
    private String appSubID;
    @JsonProperty("Time_Stamp")
    private String timeStamp;
    @JsonProperty("App_Version")
    private String appVersion;
    @JsonProperty("Partner_ID")
    private String partnerID;
    @JsonProperty("Divice_Version")
    private String diviceVersion;
    @JsonProperty("User_Token")
    private String userToken;
    @JsonProperty("Api_Version")
    private String apiVersion;
}

