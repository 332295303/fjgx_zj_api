package com.inspur.zzy.fjgx.common.core.service;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@Data
@Getter
@ConfigurationProperties(prefix = "zzy.fjgx")
public class FJConfiguationProperties {
    private Map<String, String> sap;
    private Map<String, String> zj;
    private Map<String, String> sfrz;
}