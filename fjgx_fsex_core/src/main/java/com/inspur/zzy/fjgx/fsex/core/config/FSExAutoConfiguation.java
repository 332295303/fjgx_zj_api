package com.inspur.zzy.fjgx.fsex.core.config;
import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import com.inspur.zzy.fjgx.fsex.api.FSExtend;
//import com.inspur.zzy.fjgx.fsex.api.service.FSExtend;
import com.inspur.zzy.fjgx.fsex.api.service.FJSPJZ;
import com.inspur.zzy.fjgx.fsex.core.common.FJFSExCommon;
import com.inspur.zzy.fjgx.fsex.core.domain.dao.FJSPJZDao;
import com.inspur.zzy.fjgx.fsex.core.service.FJSPJZImpl;
import com.inspur.zzy.fjgx.fsex.core.service.FSExtendImpl;
import com.inspur.zzy.fjgx.fsex.core.utils.TRTSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

@Configuration(proxyBeanMethods = false)
@Slf4j
@EnableJpaRepositories("com.inspur.zzy.fjgx.fsex.core.domain.repository")
@EntityScan("com.inspur.zzy.fjgx.fsex.core.domain.entity")
@EnableConfigurationProperties(FJConfiguationProperties.class)
public class FSExAutoConfiguation {
    public FSExAutoConfiguation() {
    }

    @Bean("com.inspur.zzy.fjgx.fsex.core.createfsex")
    public FSExtend createFSExtend(FJFSExCommon fjfsExCommon) {
        log.debug("加载FSExtend");

        return new FSExtendImpl(fjfsExCommon);
    }
    @Bean("com.inspur.zzy.fjgx.core.utils")
    public TRTSQLUtils createTRTSQLUtils(){
        log.debug("加载TRTSQLUtils");
        return  new TRTSQLUtils();
    }

    @Bean("com.inspur.zzy.fjgx.fsex.fjspjzdao")
    public FJSPJZDao createFJSPJZDao(EntityManager manager){
        log.debug("FJSPJZDao");
        return new FJSPJZDao(manager);
    }

    @Bean("com.inspur.zzy.fjgx.fsex.fjspjz")
    public FJSPJZ createFJSPJZ(FJSPJZDao dao){
        log.debug("FJSPJZ");
        return  new FJSPJZImpl(dao);
    }
    @Bean("com.inspur.zzy.fjgx.fsex.fjfsExCommon")
    public FJFSExCommon fjfsExCommon(FJConfiguationProperties properties) {
        return new FJFSExCommon(properties);
    }
}
