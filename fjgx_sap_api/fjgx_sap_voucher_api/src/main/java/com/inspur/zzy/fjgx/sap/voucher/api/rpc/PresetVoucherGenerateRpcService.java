package com.inspur.zzy.fjgx.sap.voucher.api.rpc;

import com.inspur.zzy.fjgx.sap.voucher.api.entity.FSResult;
import io.iec.edp.caf.rpc.api.annotation.GspServiceBundle;
import static com.inspur.zzy.fjgx.sap.voucher.api.common.VoucherConstants.RpcPublish.*;
import java.util.Map;
/**
 * <p>生成预制凭证rpc服务，作为共享流程凭证生成节点启动后的构件</p>
 *
 * @author hanwenzhi
 * @version 1.0
 * @date 2022/6/15
 */
@GspServiceBundle(applicationName = APP,serviceUnitName = SU,serviceName = "PresetVoucherGenerateRpcService")
public interface PresetVoucherGenerateRpcService {

    FSResult generateVoucher(Map<String, Object> map);
}
