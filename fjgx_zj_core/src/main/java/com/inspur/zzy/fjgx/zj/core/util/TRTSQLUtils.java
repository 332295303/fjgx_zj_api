package com.inspur.zzy.fjgx.zj.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.iec.edp.caf.commons.transaction.JpaTransaction;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.query.NativeQuery;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TRTSQLUtils {
    private EntityManager entityManager = SpringBeanUtils.getBean(EntityManager.class);

//    @Modifying
//    @Transactional
//    public int excuteSql(String sql) {
//        Query query = entityManager.createNativeQuery(sql);
//        int resultnum = query.executeUpdate();
//        return resultnum;
//    }

    //向资金计划表中插入数据
    //@Transactional
    public void insertCapitalPlanSql(String sql) {
        JpaTransaction tran = JpaTransaction.getTransaction();
        try {
            tran.begin();
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            tran.commit();
        }catch (Exception e){
            tran.rollback();
        }
    }
    //付款  通用报销单和 差旅费报销单更改状态
    public void  updatePayTailStatus(String date,String param,Boolean flag){
        if (flag) {
            //更新付款明细状态
            String sqlstatus = " update ROBXFK set ROBXFK_FKZT='3',ROBXFK_FKRQ=to_date('"+date+"','YYYYMMdd') where  ROBXFK_fknm='" + param + "'";
            //单据状态再说
            JpaTransaction tran = JpaTransaction.getTransaction();
            tran.begin();
            Query query = entityManager.createNativeQuery(sqlstatus);
            query.executeUpdate();
            tran.commit();
        }else {
            String sqlstatus = " update ROBXFK set ROBXFK_FKZT='3',ROBXFK_FKRQ=to_date('"+date+"','YYYYMMdd') where  ROBXFK_fknm='" + param + "'";
            //单据状态再说
            JpaTransaction tran = JpaTransaction.getTransaction();
            tran.begin();
            Query query = entityManager.createNativeQuery(sqlstatus);
            query.executeUpdate();
            tran.commit();

        }
    }

    //判断该记录资金计划表中是否已经存在
    public Boolean getExistCapitalPlanNotes(String sql) {
        Query query = entityManager.createNativeQuery(sql);

        List<Map<String, Object>> resultList = query.getResultList();
        if (resultList.size() >= 1) {
            return true;
        }
        return false;
    }

    /*
     * 根据传入sql获取JsonArray 对象
     */
    public JSONArray getResultJsonArray(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = query.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> s : resultList) {
            JSONObject jsonObject = new JSONObject();
            Set<String> keySet = s.keySet();
            for (String key : keySet) {
                jsonObject.put(key, s.get(key));
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
