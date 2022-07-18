package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReverseBusData {
    @JsonProperty("BZLNR")
    private String bzlnr;
    @JsonProperty("BUKRS")
    private String bukrs;
    @JsonProperty("BELNR")
    private String belnr;
    @JsonProperty("GJAHR")
    private String gjahr;
    @JsonProperty("STGRD")
    private String stgrd;
    @JsonProperty("BUDAT")
    private String budat;
    @JsonProperty("BZNAM")
    private String bznam;
    @JsonProperty("ZZNAM")
    private String zznam;
    @JsonProperty("RESV01")
    private String resv01;
    @JsonProperty("RESV02")
    private String resv02;
    @JsonProperty("RESV03")
    private String resv03;
    @JsonProperty("RESV04")
    private String resv04;
    @JsonProperty("RESV05")
    private String resv05;
}
