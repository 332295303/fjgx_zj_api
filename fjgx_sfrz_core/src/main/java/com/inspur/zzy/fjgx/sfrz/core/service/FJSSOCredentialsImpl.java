package com.inspur.zzy.fjgx.sfrz.core.service;

import io.iec.edp.caf.security.core.authentication.CafSecurityCredentials;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Getter
@Setter
@Slf4j
public class FJSSOCredentialsImpl implements CafSecurityCredentials {

    private String ssoCode;
    private String ssoPass;
    private String languageCode;
    private String authenStrategy;
    private String tenantId;

    public FJSSOCredentialsImpl(String ssoCode, String ssoPass, String languageCode, String authenStrategy, String tenantId) {
        log.debug("FJSSOCredentialsImpl");
        log.debug("ssoCode:" + ssoCode + ", ssoPass:" + ssoPass);
        this.ssoCode = ssoCode;
        this.ssoPass = ssoPass;
        this.languageCode = languageCode;
        this.authenStrategy = authenStrategy;
        this.tenantId = tenantId;
    }

    @Override
    public String getPrincipalId() {
        return ssoCode;
    }

    @Override
    public Optional<String> getTenantId() {
        return Optional.of(tenantId);
    }
}
