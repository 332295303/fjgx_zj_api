package com.inspur.zzy.fjgx.fsex.api.service;

import com.inspur.zzy.fjgx.fsex.api.entity.Result;
import io.iec.edp.caf.rpc.api.annotation.GspServiceBundle;
import io.iec.edp.caf.rpc.api.annotation.RpcParam;
import io.iec.edp.caf.rpc.api.annotation.RpcServiceMethod;

import java.util.Map;

@GspServiceBundle(applicationName = "zzy",serviceUnitName = "fjgx",serviceName = "fjspjz")
public interface FJSPJZ {
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fjspjz.getApproveUser")
    Result getApproveUser(@RpcParam(paramName = "inParam") Map<String,Object> inParam);

}
