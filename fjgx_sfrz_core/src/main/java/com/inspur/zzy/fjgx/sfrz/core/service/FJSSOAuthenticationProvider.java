package com.inspur.zzy.fjgx.sfrz.core.service;

import io.iec.edp.caf.security.core.UserDetails;
import io.iec.edp.caf.security.core.authentication.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FJSSOAuthenticationProvider implements CafAuthenticationProvider {
    private final FJSSOUserServiceImpl fjssoUserService;

    public FJSSOAuthenticationProvider(FJSSOUserServiceImpl userService) {
        log.debug("FJSSOAuthenticationProvider");
        this.fjssoUserService = userService;
    }
    @Override
    public UserDetails doAuthenticationInternal(CafSecurityCredentials cafSecurityCredentials) throws CafSecurityAuthenticationException {
        log.debug("doAuthenticationInternal");
        UserDetails userDetails;
        try
        {
            userDetails = fjssoUserService.loadUserByCredentials(cafSecurityCredentials);
            return userDetails;
        }
        catch (Exception e)
        {
            log.error("loadUserByCredentials出错：", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public CafSecurityAuthentication onAuthenticatedSuccess(CafSecurityCredentials cafSecurityCredentials, UserDetails userDetails) {
        log.debug("onAuthenticatedSuccess");
        return CafSecurityAuthentication.builder()
                .authenticated(true)
                .credentials(cafSecurityCredentials)
                .principal(new CafSecurityPrincipal(userDetails.getId(), CafSecurityPrincipal.Category.USER))
                .userDetails(userDetails)
                .build();
    }

    @Override
    public boolean support(Class<?> authentication) {
        log.debug("support");
        Boolean bool = FJSSOCredentialsImpl.class.equals(authentication);
        log.debug(bool.toString());
        return bool;
    }
}
