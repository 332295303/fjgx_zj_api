package com.inspur.zzy.fjgx.sfrz.core.service;

import com.inspur.zzy.fjgx.common.core.service.FJHttpUtils;
import com.inspur.zzy.fjgx.sfrz.core.domain.entity.FJGSPUserEntity;
import com.inspur.zzy.fjgx.sfrz.core.domain.repository.FJUserRepository;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import io.iec.edp.caf.security.core.UserDetails;
import io.iec.edp.caf.security.core.UserDetailsNotFoundException;
import io.iec.edp.caf.security.core.UserDetailsService;
import io.iec.edp.caf.security.core.authentication.CafSecurityAuthenticationException;
import io.iec.edp.caf.security.core.authentication.CafSecurityCredentials;
import io.iec.edp.caf.sysmanager.api.data.user.User;
import io.iec.edp.caf.sysmanager.api.manager.user.UserManager;
import io.iec.edp.caf.tenancy.api.context.MultiTenantContextInfo;
import lombok.extern.slf4j.Slf4j;
import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FJSSOUserServiceImpl implements UserDetailsService {
    UserManager userManager;

    private String ZJIP;
    public FJSSOUserServiceImpl(UserManager userManager, FJConfiguationProperties configuationProperties) {
        this.userManager = userManager;
        this.ZJIP = configuationProperties.getSfrz().get("addr");
    }
    @Override
    public UserDetails loadUserByCredentials(CafSecurityCredentials cafSecurityCredentials) throws CafSecurityAuthenticationException {
        log.debug("loadUserByCredentials");

        FJUserRepository userRepository = SpringBeanUtils.getBean(FJUserRepository.class);
        FJSSOCredentialsImpl fsscCredentials= (FJSSOCredentialsImpl) cafSecurityCredentials;

        String ssoCode = fsscCredentials.getSsoCode();
        String ssoPass = fsscCredentials.getSsoPass();

        if(!ssoIdentity(ssoCode, ssoPass)) {
            throw new CafSecurityAuthenticationException("SSO认证失败");
        }

        String tenantId = fsscCredentials.getTenantId().orElse("10000");
        MultiTenantContextInfo value = new MultiTenantContextInfo();
        value.setTenantId(Integer.parseInt(tenantId));

        value.setServiceUnit("Sys");
        String userId = "";
        try
        {
            FJGSPUserEntity userEntity = userRepository.findByCode(fsscCredentials.getSsoCode());
            userId = userEntity.getId();
        }
        catch (Exception e) {
            log.error("用户" + ssoCode + "在GS系统不存在");
            throw new CafSecurityAuthenticationException("用户" + ssoCode + "在GS系统不存在");
        }

        User userBasicInfo = userManager.getUserBasicInfo(userId);
        if (userBasicInfo == null){
            throw new UserDetailsNotFoundException("user is already delete, please check.");
        }

        Map<String, String> externalAttributes = new HashMap<>(16);
        externalAttributes.put("SysOrgId", userBasicInfo.getSysOrgId());
        externalAttributes.put("SysOrgCode", userBasicInfo.getSysOrgCode());
        externalAttributes.put("SysOrgName", userBasicInfo.getSysOrgName());
        // 构造userdetail

        UserDetails userDetails = io.iec.edp.caf.security.core.User.builder()
                .id(userBasicInfo.getId())
                .username(userBasicInfo.getCode())
                .fullName(userBasicInfo.getName())
                .firstName(null)
                .middleName(null)
                .lastName(null)
                .currentTenantId(tenantId)
                .boundLocalUser(true)
                .externalAttributes(externalAttributes)
                .build();
        log.debug(userDetails.getUsername());
        log.debug(userDetails.getUser().getName());
        return userDetails;
    }

    //sso认证
    private Boolean ssoIdentity(String ssoCode, String ssoPass) {
        String token = getTokenId(ssoCode, ssoPass);
        return valifyTokenId(token);
    }


    //获取token
    private String getTokenId(String code, String pass) {
        String tokenUrl = ZJIP + "/am/identity/authenticate?username=" + code + "&password=" + pass + "&uri=service=initService";
        String token = "";
        try {
            token = FJHttpUtils.get(tokenUrl);
            log.debug("获取token结果:" + token);
            if(token.contains("exception")) {
                throw new RuntimeException(token);
            }
            String tokenPrefix = "token.id";
            token = token.substring(tokenPrefix.length() + 1);
        } catch (IOException e) {
            throw new RuntimeException("获取资金token.id失败:" + e.getMessage());
        }
        return token;
    }

    //验证token
    private Boolean valifyTokenId(String tokenId) {
        String validUrl = ZJIP + "/am/identity/istokenvalid?tokenid=" + tokenId;
        String result = "";
        try {
            result = FJHttpUtils.get(validUrl);
            log.debug("验证result:" + result);
        } catch (IOException e) {
            throw new RuntimeException("验证资金token.id接口失败:" + e.getMessage());
        }
        if (result.equals("boolean=true"))
        {
            return true;
        }
        else
        {
            throw new RuntimeException("验证资金token.id失败" );
        }
    }
}
