package com.inspur.zzy.fjgx.zj.core.service.impl;

import com.inspur.zzy.fjgx.zj.api.entity.ZJBackInfo;
import com.inspur.zzy.fjgx.zj.api.entity.ZJResult;
import com.inspur.zzy.fjgx.zj.api.service.ZJService;
import io.iec.edp.caf.commons.transaction.JpaTransaction;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;

@Slf4j
public class ZJServiceImpl implements ZJService {
    EntityManager entityManager = SpringBeanUtils.getBean(EntityManager.class);

    @Override
    public ZJResult zjBackService(ZJBackInfo zjBackInfo) {
        String lyid = zjBackInfo.getLyid(),
                bzdid = zjBackInfo.getBzdid(),
                bzdbh = zjBackInfo.getBzdbh(),
                zfmxid = zjBackInfo.getZfmxid(),
                fkjg = zjBackInfo.getFkjg(),
                fksj = zjBackInfo.getFksj(),
                fkfs = zjBackInfo.getFkfs(),
                fkzh = zjBackInfo.getFkzh(),
                pjbh = zjBackInfo.getPjbh(),
                sbyy = zjBackInfo.getSbyy(),
                yl01 = zjBackInfo.getYl01(),
                yl02 = zjBackInfo.getYl02(),
                yl03 = zjBackInfo.getYl03(),
                yl04 = zjBackInfo.getYl04(),
                yl05 = zjBackInfo.getYl05();
        BigDecimal fkje = zjBackInfo.getFkje();

        log.debug("{}", zjBackInfo.toString());

        ZJResult zjResult = new ZJResult();

        JpaTransaction writeBackTrans=JpaTransaction.getTransaction();
        writeBackTrans.begin();
        try {
            if (StringUtils.isEmpty(lyid) || StringUtils.isEmpty(bzdid) || StringUtils.isEmpty(bzdbh) || StringUtils.isEmpty(zfmxid) ||
                    StringUtils.isEmpty(fkjg) || StringUtils.isEmpty(fksj) || StringUtils.isEmpty(fkfs) || StringUtils.isEmpty(fkzh)) {
                zjResult.setFlag("0");
                zjResult.setLyid(zjBackInfo.getLyid());
                zjResult.setMess("必填参数为空，请检查");
                return zjResult;
            }

            updateZFMX(zjBackInfo);

            zjResult.setFlag("1");
            zjResult.setLyid(lyid);
            zjResult.setMess("成功");
            writeBackTrans.commit();
            return zjResult;
        } catch (Exception e) {
            log.error("资金回写接口失败：{}", e.getMessage());
            zjResult.setFlag("0");
            zjResult.setLyid(lyid);
            zjResult.setMess(e.getMessage());
            writeBackTrans.rollback();
            return zjResult;
        }
    }

    @Override
    public ZJResult zjTest() {
        ZJResult result = new ZJResult();
        result.setMess("成功");
        result.setFlag("1");
        result.setLyid("123");
        return result;
    }

    @Override
    public ZJResult zjWriteBackService(ZJBackInfo zjBackInfo) {
        return zjBackService(zjBackInfo);
    }

    //更新支付明细
    private void updateZFMX(ZJBackInfo zjBackInfo) {
        String lyid = zjBackInfo.getLyid(),
                bzdid = zjBackInfo.getBzdid(),
                bzdbh = zjBackInfo.getBzdbh(),
                zfmxid = zjBackInfo.getZfmxid(),
                fkjg = zjBackInfo.getFkjg(),
                fksj = zjBackInfo.getFksj(),
                fkfs = zjBackInfo.getFkfs(),
                fkzh = zjBackInfo.getFkzh(),
                pjbh = zjBackInfo.getPjbh(),
                sbyy = zjBackInfo.getSbyy(),
                fkfsDesc = fkfs.equals("1")? "现金": fkfs.equals("2")? "现汇": "票据";
        BigDecimal fkje = zjBackInfo.getFkje();
        String xzzfFiled = "ROBXFK_SZ3"; //现金支付金额字段
        String xhzfField = "ROBXFK_SZ4"; //现汇支付金额字段
        String pjzfField = "ROBXFK_SZ5"; //票据支付金额字段
        String updateSql = "";
        String zfxx = "";
        Query query = null;

        if(fkjg.equals("1")) { //付款成功
            updateSql = "update ROBXFK set %s = " + fkje + " where ROBXFK_FKNM = '" + zfmxid + "'";
            if(fkfs.equals("1")) { //现金支付
                updateSql = String.format(updateSql, xzzfFiled);
            } else if(fkfs.equals("2")) { //现汇支付
                updateSql = String.format(updateSql, xhzfField);
            } else { //票据支付
                updateSql = String.format(updateSql, pjzfField);
            }
            log.debug("updateSql:{}", updateSql);
            query = entityManager.createNativeQuery(updateSql);
            query.executeUpdate();

            //插入执行记录
            String insertSql = "insert into BPBIZPAYTREQPAYTEXECTRECD(ID, SRCDOCID, SRCDOCNO, PAYDATE, PAYLOCALAMOUNT, PAYCURRENCY, PAYAMOUNT, ISDIFFCURRPAY, SETTLEWAY, PAYACCOUNT, RECEIVINGUNIT, RECEIVINGACCOUNT, RECEIVINGACCOUNTNO, RECEIVINGACCOUNTNAME, RECEIVINGACCOUNTBANKNAME)\n" +
                    "select SYS_GUID(), ROBXFK_BXNM, '" + bzdbh + "', TO_DATE('" + fksj + "', 'YYYY-MM-DD HH24:MI:ss'), " + fkje + ", 'CNY', " + fkje + ", '0', '" + fkfs + "', '" + fkzh + "', ROBXFK_DFBH, ROBXFK_DFMC, ROBXFK_DFZH, ROBXFK_DFZHM, ROBXFK_DFYH\n" +
                    "from ROBXFK\n" +
                    "where ROBXFK_FKNM = '" + zfmxid + "'";
            log.debug("insertSql:{}", insertSql);

            query = entityManager.createNativeQuery(insertSql);
            query.executeUpdate();

            zfxx = "付款方式：" + fkfsDesc + ",付款时间：" + fksj + ",付款金额：" + fkje + ";";
        } else {
            zfxx = "付款方式：" + fkfsDesc + ",失败原因：" + sbyy + ";";
        }
        //更新支付信息
        String updateZFXXSql = "update ROBXFK set ROBXFK_PARTNERADRESSMC = ROBXFK_PARTNERADRESSMC || '" + zfxx + "' where ROBXFK_FKNM = '" + zfmxid + "'";
        log.debug("updateZFXXSql:{}", updateZFXXSql);
        query = entityManager.createNativeQuery(updateZFXXSql);
        query.executeUpdate();
    }
}
