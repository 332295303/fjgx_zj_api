package com.inspur.zzy.fjgx.fsex.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.iec.edp.caf.commons.transaction.JpaTransaction;
import io.iec.edp.caf.commons.utils.SpringBeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TRTSQLUtils {
    private EntityManager entityManager = SpringBeanUtils.getBean(EntityManager.class);

    //付款单接口返回结果回写支付信息
    public void  updatePayTail(String sql){
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

    //执行报账单查询语句
    public    JSONArray selectResultSroTra(String sql){
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



    //2付款单接口返回结果回写支付信息
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
