package com.inspur.zzy.fjgx.sfrz.core.config;

import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import com.inspur.zzy.fjgx.sfrz.core.service.FJSSOAuthProvider;
import com.inspur.zzy.fjgx.sfrz.core.service.FJSSOUserServiceImpl;
import io.iec.edp.caf.sysmanager.api.manager.user.UserManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(FJConfiguationProperties.class)
@EnableJpaRepositories("com.inspur.zzy.fjgx.sfrz.core.domain.repository")
@EntityScan("com.inspur.zzy.fjgx.sfrz.core.domain.entity")
public class SFRZAutoConfiguation {
    @Bean("com.inspur.zzy.fjgx.sfrz.userservice")
    public FJSSOUserServiceImpl fjssoUserService(UserManager userManager, FJConfiguationProperties configuationProperties){
        return new FJSSOUserServiceImpl(userManager, configuationProperties);
    }
    @Bean("com.inspur.zzy.fjgx.sfrz.authprovider")
    public FJSSOAuthProvider fjssoAuthProvider(){
        return new FJSSOAuthProvider();
    }
}
