package com.inspur.zzy.fjgx.qywx.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inspur.gs.bf.df.fssp.pf.api.entity.PFTask;
import com.inspur.gs.bf.df.fssp.pf.api.entity.PFTaskContext;
import com.inspur.gs.bf.df.fssp.pf.api.entity.PFTaskEventArgs;
import com.inspur.gs.bf.df.fssp.pf.api.service.PFTaskEventListener;
import com.inspur.zzy.fjgx.fsex.core.utils.TRTSQLUtils;
import com.inspur.zzy.fjgx.qywx.core.common.QywxCommon;
import com.inspur.zzy.fjgx.qywx.core.common.SQLQywxCommon;
import com.inspur.zzy.fjgx.qywx.core.common.URLWxAskCommon;
import com.inspur.zzy.fjgx.qywx.core.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.util.List;

@Slf4j

public class PFTaskEventListenerEx implements PFTaskEventListener {
    @Autowired
    private TRTSQLUtils TRTSQLUtils;
    @Override
    public void pfTaskCreatedEvent(PFTaskEventArgs pfTaskEventArgs) {
        log.error("共享待办任务创建");
        PFTaskContext pfTaskContext = pfTaskEventArgs.getPFTaskContext();
        String nodeType = pfTaskContext.getNodeType();
        List<PFTask> currentPFTasks = pfTaskContext.getCurrentPFTasks();
        int size = currentPFTasks.size();
        log.info("size"+size);
        for (int i=0;i<size;i++) {
            PFTask pfTask = currentPFTasks.get(i);
            String assignee = pfTask.getAssignee();//找到编号 企业微信根据编号进行消息发送
            //根据id查询到用户编码
            JSONArray jsonArray = TRTSQLUtils.selectResultSroTra(SQLQywxCommon.getUserCode(assignee));
            if (!(jsonArray.size() > 0)) {
                log.error("未查到下一节点的负责人CODE");
                return;
            }
            String userCode=jsonArray.getJSONObject(0).getString("CODE") ;

            //Url
            String s = HttpUtils.doGet(URLWxAskCommon.getUrlToken);
            log.info("获取token"+s);
            JSONObject getJson = (JSONObject)JSONObject.parse(s);
            String errcode = getJson.getString("errcode");
            if (!"0".equals(errcode)) {
                log.error("获取token失败："+getJson.getString("errmsg"));
                return;
            }
            log.error("获取token成功："+getJson.getString("errmsg"));
            String access_token = getJson.getString("access_token");
            String body = QywxCommon.getBody(userCode, "1000005", "text", "0", "0", "0", "1800", "", "", "测试发送消息11213");

            try {
                log.info("发送报文"+body);
                JSONObject jsonObject = HttpUtils.doPost(URLWxAskCommon.getUrlDoSend + access_token, body);
                String posterrcode = jsonObject.getString("errcode");
                if (!"0".equals(posterrcode)){
                    log.error("发送POST请求失败"+jsonObject.getString("errmsg"));
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
                log.error("发送提醒报错"+e.getMessage()+".....");
            }


            log.info("发送审核消息提醒成功");
        }
    }

    @Override
    public void pfTaskCompletedEvent(PFTaskEventArgs pfTaskEventArgs) {
        log.error("进入共享任务完成事件");
        PFTaskContext pfTaskContext = pfTaskEventArgs.getPFTaskContext();
        String nodeType = pfTaskContext.getNodeType();
        List<PFTask> currentPFTasks = pfTaskContext.getCurrentPFTasks();
        PFTask pfTask = currentPFTasks.get(0);
        pfTask.getAssignee();
        log.error("2pfTaskCompletedEvent");
    }

    @Override
    public void pfTaskDeletedEvent(PFTaskEventArgs pfTaskEventArgs) {
        PFTaskContext pfTaskContext = pfTaskEventArgs.getPFTaskContext();
        String nodeType = pfTaskContext.getNodeType();

        log.error("3pfTaskDeletedEvent");
        log.error("进入共享任务删除事件");
    }

}
