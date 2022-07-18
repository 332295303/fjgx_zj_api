package com.inspur.zzy.fjgx.zj.core.config;

import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import com.inspur.zzy.fjgx.zj.api.service.ZJService;
import com.inspur.zzy.fjgx.zj.core.common.FJZJCommon;
import com.inspur.zzy.fjgx.zj.core.service.impl.CapitalPlan;
import com.inspur.zzy.fjgx.zj.core.service.impl.ZJServiceImpl;
import com.inspur.zzy.fjgx.zj.core.util.TRTSQLUtils;
import io.iec.edp.caf.rest.RESTEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@EnableConfigurationProperties(FJConfiguationProperties.class)
@ComponentScan("com.inspur.zzy.fjgx")
@Configuration
public class ZJAutoConfiguation {
    @Bean("com.inspur.zzy.fjgx.zj.registerZJService")
    public RESTEndpoint registerZJService() {
        ZJService zjService = new ZJServiceImpl();
        return new RESTEndpoint("/zzy/fjgx/v1.0/service/zj", new Object[]{zjService});
    }
    @Bean("45ea0722-be23-8180-603b-51c57e433a83")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CapitalPlan getcall_method(FJZJCommon fjzjCommon) {
        return new CapitalPlan(fjzjCommon);
    }

    @Bean("com.inspur.zzy.fjgx.zj.core.util")
    public TRTSQLUtils trtsqlUtils() {
        return new TRTSQLUtils();
    }

    @Bean("com.inspur.zzy.fjgx.zj.fjzjCommon")
    public FJZJCommon fjzjCommon(FJConfiguationProperties properties) {
        return new FJZJCommon(properties);
    }
}
