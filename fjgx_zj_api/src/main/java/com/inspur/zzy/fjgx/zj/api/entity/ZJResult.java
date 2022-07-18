package com.inspur.zzy.fjgx.zj.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZJResult {
    @JsonProperty("LYID")
    private String lyid;
    @JsonProperty("FLAG")
    private String flag;
    @JsonProperty("MESS")
    private String mess;
}
