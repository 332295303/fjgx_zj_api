package com.inspur.zzy.fjgx.fsex.core.service;

import com.inspur.zzy.fjgx.fsex.api.entity.Result;
import com.inspur.zzy.fjgx.fsex.api.service.FJSPJZ;
import com.inspur.zzy.fjgx.fsex.core.domain.dao.FJSPJZDao;
import com.inspur.zzy.fjgx.fsex.core.domain.entity.FJSPJZEntity;
import com.inspur.zzy.fjgx.fsex.core.domain.repository.FJSPJZRepository;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.NotSupportedException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class FJSPJZImpl implements FJSPJZ {
    FJSPJZDao fjspjzDao;

    public FJSPJZImpl(FJSPJZDao dao) {
        this.fjspjzDao = dao;
    }
    @Override
    public Result getApproveUser(Map<String,Object> inParam) {

        log.info("进入getApprover");
        FJSPJZRepository spjzRepository = SpringBeanUtils.getBean(FJSPJZRepository.class);
        Map<String,Object> context= (Map<String, Object>) inParam.get("contextParam");
        String billid = Objects.nonNull(context.get("BILLID"))? context.get("BILLID").toString(): "";

        log.info("单据内码:{}",billid);
            /*"methodParam": {
                "xzbmid": "9ce6afef-da99-d9b9-794d-7dfdd2256e69;ROFYFT;ROFYFT_BXNM",
                "jd": "9ce6afef-da99-d9b9-794d-7dfdd2256e69;ROBXDJ;ROBXDJ_DWID",
           }*/

        Result result;
        if(billid.length() == 0) {
            result = Result.success("");
            return result;
        }

        try
        {
            Map<String,Object> methodParams= (Map<String, Object>) inParam.get("methodParam");
            String idField = Optional.ofNullable((String) methodParams.get("idField")).orElse("");
            String xzbmField = Optional.ofNullable((String) methodParams.get("xzbmField")).orElse("");
            String jd = Optional.ofNullable((String) methodParams.get("jd")).orElse("");

            if(StringUtils.isEmpty(idField) || StringUtils.isEmpty(xzbmField) || StringUtils.isEmpty(jd)){
                throw new IllegalArgumentException("获取审批人失败：必填参数为空");
            }

            log.info("id字段：{}，行政部门字段:{},节点：{}",idField, xzbmField, jd);
            String[] xzbmFields = xzbmField.split(";");
            String tableName = xzbmFields[1];
            String bmFieldName = xzbmFields[2];
            String idFieldName = idField.split(";")[2];
            Map<String, Object> xzbmMap = fjspjzDao.getXZBM(billid, tableName, bmFieldName, idFieldName);
            if(xzbmMap == null) {
                throw new IllegalArgumentException("获取行政部门失败");
            }
            String xzbmid = xzbmMap.get(bmFieldName).toString();

            log.info("行政部门id:{}",xzbmid);
            FJSPJZEntity fjspjzEntity = spjzRepository.findByXzbmid(xzbmid);
            if(fjspjzEntity == null) {
                log.debug("未获取到审批人");
                result = Result.success("");
                return result;
            }
            String approveUser = "";
            switch (jd){
                case "JD1":
                    approveUser = fjspjzEntity.getJd1id();
                    break;
                case "JD2":
                    approveUser = fjspjzEntity.getJd2id();
                    break;
                case "JD3":
                    approveUser = fjspjzEntity.getJd3id();
                    break;
                case "JD4":
                    approveUser = fjspjzEntity.getJd4id();
                    break;
                case "JD5":
                    approveUser = fjspjzEntity.getJd5id();
                    break;
                case "JD6":
                    approveUser = fjspjzEntity.getJd6id();
                    break;
                case "JD7":
                    approveUser = fjspjzEntity.getJd7id();
                    break;
                case "JD8":
                    approveUser = fjspjzEntity.getJd8id();
                    break;
                case "JD9":
                    approveUser = fjspjzEntity.getJd9id();
                    break;
                case "JD10":
                    approveUser = fjspjzEntity.getJd10id();
                    break;
                case "JD11":
                    approveUser = fjspjzEntity.getJd11id();
                    break;
                case "JD12":
                    approveUser = fjspjzEntity.getJd12id();
                    break;
                case "JD13":
                    approveUser = fjspjzEntity.getJd13id();
                    break;
                case "JD14":
                    approveUser = fjspjzEntity.getJd14id();
                    break;
                case "JD15":
                    approveUser = fjspjzEntity.getJd15id();
                    break;
                default:
                    throw new IllegalArgumentException("节点参数"+ jd +"不正确");
            }
            log.debug("approver:{}", approveUser);
            result = Result.success(approveUser);
            return result;
        }
        catch (IllegalArgumentException e1) {
            result = Result.error(e1.getMessage());
            return result;
        }
        catch (Exception e) {
            log.error("获取审批人出错：{}", e.getMessage(), e);
            result = Result.error(e.getMessage());
            return result;
        }
    }
}
