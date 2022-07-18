package com.inspur.zzy.fjgx.sap.voucher.core.config;

import com.inspur.zzy.fjgx.common.core.service.FJConfiguationProperties;
import com.inspur.zzy.fjgx.sap.voucher.api.rpc.SAPVoucherRpcService;
import com.inspur.zzy.fjgx.sap.voucher.core.rpc.SAPVoucherRpcServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * <p>spring配置类</p>
 * @author hanwenzhi
 * @date 2022/6/16
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FJConfiguationProperties.class)
public class VoucherAutoConfiguration {

    public VoucherAutoConfiguration() {
    }


    @Bean("com.inspur.zzy.fjgx.sap.voucher.createvoucher")
    public SAPVoucherRpcService createSAPVoucher(FJConfiguationProperties configuationProperties) {
        return new SAPVoucherRpcServiceImpl(configuationProperties);
    }
}
