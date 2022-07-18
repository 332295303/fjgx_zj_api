package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VoucherEngineResult {
    @JsonProperty("asyn")
    private String asyn;
    @JsonProperty("data")
    private List<VoucherEngineResultData> dataList;
}
