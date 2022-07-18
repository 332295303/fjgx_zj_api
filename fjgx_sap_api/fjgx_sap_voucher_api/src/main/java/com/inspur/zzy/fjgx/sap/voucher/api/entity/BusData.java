package com.inspur.zzy.fjgx.sap.voucher.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.iec.edp.caf.common.JSONSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.List;

@Setter
public class BusData {
    @JsonProperty("HTAB")
    private BusDataHeader busDataHeader;

    @JsonProperty("ITAB")
    private List<BusDataItem> busDataItemList;
}
