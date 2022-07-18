package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDataItem {
    @JsonProperty("BZLNR")
    private String BZLNR;
    @JsonProperty("BZZEI")
    private String BZZEI;
    @JsonProperty("BSCHL")
    private String BSCHL;
    @JsonProperty("SHKZG")
    private String SHKZG;
    @JsonProperty("HKONT")
    private String HKONT;
    @JsonProperty("UMSKZ")
    private String UMSKZ;
    @JsonProperty("KOSTL")
    private String KOSTL;
    @JsonProperty("KUNNR")
    private String KUNNR;
    @JsonProperty("LIFNR")
    private String LIFNR;
    @JsonProperty("VBUND")
    private String VBUND;
    @JsonProperty("MWSKZ")
    private String MWSKZ;
    @JsonProperty("ZFBDT")
    private String ZFBDT;
    @JsonProperty("RSTGR")
    private String RSTGR;
    @JsonProperty("ZUONR")
    private String ZUONR;
    @JsonProperty("SGTXT")
    private String SGTXT;
    @JsonProperty("DMBTR")
    private String DMBTR;
    @JsonProperty("RESV01")
    private String RESV01;
    @JsonProperty("RESV02")
    private String RESV02;
    @JsonProperty("RESV03")
    private String RESV03;
    @JsonProperty("RESV04")
    private String RESV04;
    @JsonProperty("RESV05")
    private String RESV05;
    @JsonProperty("RESV06")
    private String RESV06;
    @JsonProperty("RESV07")
    private String RESV07;
    @JsonProperty("RESV08")
    private String RESV08;
    @JsonProperty("RESV09")
    private String RESV09;
    @JsonProperty("RESV10")
    private String RESV10;

    @Override
    public String toString() {
        return "BusDataItem{" +
                "BZLNR='" + BZLNR + '\'' +
                ", BZZEI='" + BZZEI + '\'' +
                ", BSCHL='" + BSCHL + '\'' +
                ", SHKZG='" + SHKZG + '\'' +
                ", HKONT='" + HKONT + '\'' +
                ", UMSKZ='" + UMSKZ + '\'' +
                ", KOSTL='" + KOSTL + '\'' +
                ", KUNNR='" + KUNNR + '\'' +
                ", LIFNR='" + LIFNR + '\'' +
                ", VBUND='" + VBUND + '\'' +
                ", MWSKZ='" + MWSKZ + '\'' +
                ", ZFBDT='" + ZFBDT + '\'' +
                ", RSTGR='" + RSTGR + '\'' +
                ", ZUONR='" + ZUONR + '\'' +
                ", SGTXT='" + SGTXT + '\'' +
                ", DMBTR='" + DMBTR + '\'' +
                ", RESV01='" + RESV01 + '\'' +
                ", RESV02='" + RESV02 + '\'' +
                ", RESV03='" + RESV03 + '\'' +
                ", RESV04='" + RESV04 + '\'' +
                ", RESV05='" + RESV05 + '\'' +
                ", RESV06='" + RESV06 + '\'' +
                ", RESV07='" + RESV07 + '\'' +
                ", RESV08='" + RESV08 + '\'' +
                ", RESV09='" + RESV09 + '\'' +
                ", RESV10='" + RESV10 + '\'' +
                '}';
    }
}
