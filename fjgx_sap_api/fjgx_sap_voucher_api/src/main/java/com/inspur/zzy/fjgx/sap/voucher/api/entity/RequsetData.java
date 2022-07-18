package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.iec.edp.caf.common.JSONSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Base64;

@Getter
@Setter
public class RequsetData {
    @JsonProperty("BUS_PARA")
    private String busPara;
    @JsonProperty("BUS_DATA")
    private String busData;

    public void setBusPara(BusPara para) throws IOException {
        String json = JSONSerializer.serialize(para);
        this.busPara = Base64.getEncoder().encodeToString(json.getBytes("UTF-8"));
    }

    public void setBusData(BusData data) throws IOException{
        String json = JSONSerializer.serialize(data);
        this.busData = Base64.getEncoder().encodeToString(json.getBytes("UTF-8"));
    }
}
