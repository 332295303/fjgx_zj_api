package com.inspur.zzy.fjgx.sfrz.core.service;

import io.iec.edp.caf.security.core.spring.authentication.CafSecuritySpringAuthentication;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class FJSSOAuthentication extends CafSecuritySpringAuthentication<FJSSOCredentialsImpl> {
    protected FJSSOAuthentication(HttpServletRequest request) {
        super(request);
        log.debug("FJSSOAuthentication");
        this.credentials = new FJSSOCredentialsImpl(request.getParameter("ssoCode"),request.getParameter("ssoPass"),
                request.getParameter("language"), request.getParameter("Authenstrategy"), request.getParameter("Tenant"));
    }
}
