package com.inspur.zzy.fjgx.zj.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZJBackInfo {
    @JsonProperty("LYID")
    private String lyid;
    @JsonProperty("BZDID")
    private String bzdid;
    @JsonProperty("BZDBH")
    private String bzdbh;
    @JsonProperty("ZFMXID")
    private String zfmxid;
    @JsonProperty("FKJG")
    private String fkjg;
    @JsonProperty("FKSJ")
    private String fksj;
    @JsonProperty("FKFS")
    private String fkfs;
    @JsonProperty("FKJE")
    private BigDecimal fkje;
    @JsonProperty("FKZH")
    private String fkzh;
    @JsonProperty("PJBH")
    private String pjbh;
    @JsonProperty("SBYY")
    private String sbyy;
    @JsonProperty("YL01")
    private String yl01;
    @JsonProperty("YL02")
    private String yl02;
    @JsonProperty("YL03")
    private String yl03;
    @JsonProperty("YL04")
    private String yl04;
    @JsonProperty("YL05")
    private String yl05;

    @Override
    public String toString() {
        return "ZJBackInfo{" +
                "lyid='" + lyid + '\'' +
                ", bzdid='" + bzdid + '\'' +
                ", bzdbh='" + bzdbh + '\'' +
                ", zfmxid='" + zfmxid + '\'' +
                ", fkjg='" + fkjg + '\'' +
                ", fksj='" + fksj + '\'' +
                ", fkfs='" + fkfs + '\'' +
                ", fkje='" + fkje + '\'' +
                ", fkzh='" + fkzh + '\'' +
                ", pjbh='" + pjbh + '\'' +
                ", sbyy='" + sbyy + '\'' +
                ", yl01='" + yl01 + '\'' +
                ", yl02='" + yl02 + '\'' +
                ", yl03='" + yl03 + '\'' +
                ", yl04='" + yl04 + '\'' +
                ", yl05='" + yl05 + '\'' +
                '}';
    }
}
