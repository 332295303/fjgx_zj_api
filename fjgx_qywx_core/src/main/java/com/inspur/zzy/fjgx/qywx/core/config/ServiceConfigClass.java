package com.inspur.zzy.fjgx.qywx.core.config;
import com.inspur.zzy.fjgx.fsex.core.utils.TRTSQLUtils;
import com.inspur.zzy.fjgx.qywx.core.service.PFTaskEventListenerEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class ServiceConfigClass {
    @Bean("inspur.zzy.wxtz.core.service")
    public PFTaskEventListenerEx getPFTaskEventListenerEx(){
        log.info("加载PFTaskEventListenerEx");
        return new PFTaskEventListenerEx();
    }


}
