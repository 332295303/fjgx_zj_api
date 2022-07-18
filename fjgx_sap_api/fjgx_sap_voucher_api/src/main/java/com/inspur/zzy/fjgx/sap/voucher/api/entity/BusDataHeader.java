package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDataHeader {
    @JsonProperty("BZART")
    private String BZART;
    @JsonProperty("BZLNR")
    private String BZLNR;
    @JsonProperty("BUKRS")
    private String BUKRS;
    @JsonProperty("BLART")
    private String BLART;
    @JsonProperty("BLDAT")
    private String BLDAT;
    @JsonProperty("BUDAT")
    private String BUDAT;
    @JsonProperty("BKTXT")
    private String BKTXT;
    @JsonProperty("WAERS")
    private String WAERS;
    @JsonProperty("KURSF")
    private String KURSF;
    @JsonProperty("NUMPG")
    private String NUMPG;
    @JsonProperty("BZNAM")
    private String BZNAM;
    @JsonProperty("ZZNAM")
    private String ZZNAM;
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
        return "BusDataHeader{" +
                "BZART='" + BZART + '\'' +
                ", BZLNR='" + BZLNR + '\'' +
                ", BUKRS='" + BUKRS + '\'' +
                ", BLART='" + BLART + '\'' +
                ", BLDAT='" + BLDAT + '\'' +
                ", BUDAT='" + BUDAT + '\'' +
                ", BKTXT='" + BKTXT + '\'' +
                ", WAERS='" + WAERS + '\'' +
                ", KURSF='" + KURSF + '\'' +
                ", NUMPG='" + NUMPG + '\'' +
                ", BZNAM='" + BZNAM + '\'' +
                ", ZZNAM='" + ZZNAM + '\'' +
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
