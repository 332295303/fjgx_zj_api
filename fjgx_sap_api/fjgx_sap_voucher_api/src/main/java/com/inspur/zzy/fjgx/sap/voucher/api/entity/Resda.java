package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Setter
public class Resda {
    @JsonProperty("Resda")
    private String resda;

    public String getResda() {
        String decode = new String(Base64.getDecoder().decode(resda));
        return decode;
    }
}
