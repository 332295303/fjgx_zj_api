package com.inspur.zzy.fjgx.fsex.api;

import io.iec.edp.caf.rpc.api.annotation.GspServiceBundle;
import io.iec.edp.caf.rpc.api.annotation.RpcParam;
import io.iec.edp.caf.rpc.api.annotation.RpcServiceMethod;

import java.util.Map;

/**111
 * <p>用于预制凭证推送或撤销凭证的服务.</p>
 * @author hanwenzhi
 * @date 2022/6/15
 * @version  1.0
 */
@GspServiceBundle(applicationName = "zzy",serviceUnitName = "fjgx",serviceName = "fsextend")
public interface FSExtend {
    /*
     * 差旅报销提交前
     *
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.submitCLbefore")
    Map<String, Object> submitCLbefore(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);


    /*
          insert into FSSPCOMPONENTINVOKE(
          FSSPCOMPONENTINVOKE_NM, --1
          Fsspcomponentinvoke_Czfid,--2
          Fsspcomponentinvoke_Czid, --3
          Fsspcomponentinvoke_Czmc,--4
          FSSPCOMPONENTINVOKE_GjID,--5
          FSSPCOMPONENTINVOKE_GjMC,--6
          Fsspcomponentinvoke_Invoketype,--7
          Fsspcomponentinvoke_Mpath, --8
          Fsspcomponentinvoke_Sname,--9
          Fsspcomponentinvoke_Su,--10
           FSSPCOMPONENTINVOKE_YWLYID,--11
          FSSPCOMPONENTINVOKE_YWLYMC,--12
           postdatatype--13
          )
          values
          (
          'fjgx_fsex_RSSubmitAfter', --1
          'FSBZ',--2
          'RSSubmitAfter', --3
          '单据提交占用资金计划',--4
          'fjgx_fsex_RSSubmitAfter',--5
           '单据提交占用资金计划',--6
          'platformrpc',--7
          'com.inspur.zzy.fjgx.fsex.RSSubmitBefore',--8
          'fsextend',--9
          'fjgx',--10
          'FSROBX',--11
          '报销单',--12
          'map'--13
          )
     */
    /*
     * 报账单编辑界面提交前
     *
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.RSSubmitBefore")
    Map<String, Object> RSSubmitAfter(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);


    /*
     * 报账单编辑界面撤销前
     *
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.backBeforeRS")
    Map<String, Object> backBeforeRS(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);


    /*
     * 触发节点后扩展
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.triggerNode")
    Map<String, Object> afterTriggerNode(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);

    /*
     * 提交扩展事件
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.submit")
    Map<String, Object> submit(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);
    /*
         insert into FSSPCOMPONENTINVOKE(
         FSSPCOMPONENTINVOKE_NM, --1
         Fsspcomponentinvoke_Czfid,--2
         Fsspcomponentinvoke_Czid, --3
         Fsspcomponentinvoke_Czmc,--4
         FSSPCOMPONENTINVOKE_GjID,--5
         FSSPCOMPONENTINVOKE_GjMC,--6
         Fsspcomponentinvoke_Invoketype,--7
         Fsspcomponentinvoke_Mpath, --8
         Fsspcomponentinvoke_Sname,--9
         Fsspcomponentinvoke_Su,--10
          FSSPCOMPONENTINVOKE_YWLYID,--11
         FSSPCOMPONENTINVOKE_YWLYMC,--12
          postdatatype--13
         )
         values
         (
         'fjgx_fsex_RSRetractAfter', --1
         'FSBZ',--2
         'RSRetractAfter', --3
         '单据撤回释放资金计划',--4
         'fjgx_fsex_RSRetractAfter',--5
          '单据撤回释放资金计划',--6
         'platformrpc',--7
         'com.inspur.zzy.fjgx.fsex.retract',--8
         'fsextend',--9
         'fjgx',--10
         'FSROBX',--11
         '报销单',--12
         'map'--13
         )
     */
    /*
     * 撤回扩展事件
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.retract")
    Map<String, Object> retractAfter(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);
    /*

        insert into FSSPCOMPONENTINVOKE(
             FSSPCOMPONENTINVOKE_NM, --1
             Fsspcomponentinvoke_Czfid,--2
             Fsspcomponentinvoke_Czid, --3
             Fsspcomponentinvoke_Czmc,--4
             FSSPCOMPONENTINVOKE_GjID,--5
             FSSPCOMPONENTINVOKE_GjMC,--6
             Fsspcomponentinvoke_Invoketype,--7
             Fsspcomponentinvoke_Mpath, --8
             Fsspcomponentinvoke_Sname,--9
             Fsspcomponentinvoke_Su,--10
              FSSPCOMPONENTINVOKE_YWLYID,--11
             FSSPCOMPONENTINVOKE_YWLYMC,--12
              postdatatype--13
             )
             values
             (
             'fjgx_fsex_RSApproveBackAfter', --1
             'FSBZ',--2
             'RSApproveBackAfter', --3
             '单据驳回释放资金计划',--4
             'fjgx_fsex_RSApproveBackAfter',--5
              '单据驳回释放资金计划',--6
             'platformrpc',--7
             'com.inspur.zzy.fjgx.fsex.approveback',--8
             'fsextend',--9
             'fjgx',--10
             'FSROBX',--11
             '报销单',--12
             'map'--13
             )
     */
    /*
     *退回后扩展
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.approveback")
    Map<String, Object> approvebackAfter(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);

    /*
     *流程完成扩展
     * */

    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.complete")
    Map<String, Object> complete(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);

    /*
      创建审核通过语句
               insert into FSSPCOMPONENTINVOKE(
              FSSPCOMPONENTINVOKE_NM, --1
              Fsspcomponentinvoke_Czfid,--2
              Fsspcomponentinvoke_Czid, --3
              Fsspcomponentinvoke_Czmc,--4
              FSSPCOMPONENTINVOKE_GjID,--5
              FSSPCOMPONENTINVOKE_GjMC,--6
              Fsspcomponentinvoke_Invoketype,--7
              Fsspcomponentinvoke_Mpath, --8
              Fsspcomponentinvoke_Sname,--9
              Fsspcomponentinvoke_Su,--10
               FSSPCOMPONENTINVOKE_YWLYID,--11
              FSSPCOMPONENTINVOKE_YWLYMC,--12
               postdatatype--13
              )
              values
              (
              'fjgx_fsex_approvepass', --1
              'FSSP',--2
              'RSApprovePassAfter', --3
              '单据审核通过',--4
              'fjgx_fsex_approvepass',--5
               '单据审核通过',--6
              'platformrpc',--7
              'com.inspur.zzy.fjgx.fsex.approvepass',--8
              'fsextend',--9
              'fjgx',--10
              'WBCOMP',--11
              '报销单',--12
              'map'--13
              )

       */
    /*
     *流程审核通过完成扩展
     * */
    @RpcServiceMethod(serviceId = "com.inspur.zzy.fjgx.fsex.approvepass")
    Map<String, Object> approvepassAfter(@RpcParam(paramName = "wbparam") Map<String, Object> paraMap);

}
