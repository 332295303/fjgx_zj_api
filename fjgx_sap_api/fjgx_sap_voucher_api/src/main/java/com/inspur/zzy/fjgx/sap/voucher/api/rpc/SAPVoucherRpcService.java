package com.inspur.zzy.fjgx.sap.voucher.api.rpc;

import io.iec.edp.caf.rpc.api.annotation.GspServiceBundle;
import io.iec.edp.caf.rpc.api.annotation.RpcParam;
import io.iec.edp.caf.rpc.api.annotation.RpcServiceMethod;

import java.util.Map;

import static com.inspur.zzy.fjgx.sap.voucher.api.common.VoucherConstants.RpcPublish.*;

/**
 * <p>用于预制凭证推送或撤销凭证的服务.</p>
 * @author hanwenzhi
 * @date 2022/6/15
 * @version  1.0
 */
@GspServiceBundle(applicationName = APP,serviceUnitName = SU,serviceName = "voucherpushorcancel")
public interface SAPVoucherRpcService {

    /**
     * <p>推送或取消sap凭证，根据参数"operation":"push/cancel".</p>
     * @param map {@link String} 序列化后的凭证信息
     * @return {@link String} 返回给预制凭证平台序列化后的推送结果信息
     */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.sap.voucher.api.rpc.SAPVoucherRpcService.pushOrCancel")
    String pushOrCancel(@RpcParam(paramName = "voucherDatas") String map);

    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.sap.voucher.api.rpc.SAPVoucherRpcService.afterGenerate")
    Map<String, Object> afterGenerate(@RpcParam(paramName = "voucherDatas") Map<String, Object> map);
}
