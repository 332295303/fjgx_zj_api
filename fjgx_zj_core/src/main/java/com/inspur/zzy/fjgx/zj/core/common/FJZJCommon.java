package com.inspur.zzy.fjgx.zj.core.common;

import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import lombok.Getter;

@Getter
public class FJZJCommon {
    //控制金额
    public FJZJCommon(FJConfiguationProperties fjConfiguationProperties) {
        this.addr = fjConfiguationProperties.getZj().get("addr");
        this.pjqsje = fjConfiguationProperties.getZj().get("pjqsje");
        this.xhzfbl = fjConfiguationProperties.getZj().get("xhzfbl");
    }
    //控制金额
    private String pjqsje;
    private String xhzfbl;
    private String addr;
}
