package com.inspur.zzy.fjgx.fsex.core.common;

import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import lombok.Getter;

@Getter
public class FJFSExCommon {
    public FJFSExCommon(FJConfiguationProperties fjConfiguationProperties) {
        this.addr = fjConfiguationProperties.getZj().get("addr");
        this.pjqsje = fjConfiguationProperties.getZj().get("pjqsje");
        this.xhzfbl = fjConfiguationProperties.getZj().get("xhzfbl");
    }
    //控制金额
    private String pjqsje;
    private String xhzfbl;
    private String addr;
}
