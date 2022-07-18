package com.inspur.zzy.fjgx.sfrz.core.service;

import io.iec.edp.caf.authentication.api.manager.Provider.authenProvider;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import io.iec.edp.caf.security.core.authentication.CafAuthenticationProvider;
import io.iec.edp.caf.security.core.authentication.CafSecurityCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class FJSSOAuthProvider implements authenProvider {

    private final FJSSOUserServiceImpl fjssoUserService;
    public FJSSOAuthProvider() {
        this.fjssoUserService = SpringBeanUtils.getBean(FJSSOUserServiceImpl.class);
    }
    @Override
    public CafAuthenticationProvider getAuthenProvider() {
        log.debug("getAuthenProvider");
        return new FJSSOAuthenticationProvider(fjssoUserService);
    }
    @Override
    public CafSecurityCredentials getCredentials(HttpServletRequest request) {
        log.debug("getCredentials");
        Authentication authentication = new FJSSOAuthentication(request);
        return (FJSSOCredentialsImpl) authentication.getCredentials();
    }
}
