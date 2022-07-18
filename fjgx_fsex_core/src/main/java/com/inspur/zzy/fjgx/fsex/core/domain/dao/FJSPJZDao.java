package com.inspur.zzy.fjgx.fsex.core.domain.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
public class FJSPJZDao {
    private EntityManager manager;

    public FJSPJZDao(EntityManager manager){
        this.manager=manager;
    }

    String query = "select %s from %s where %s=:bxnm";
    public Map<String, Object> getXZBM(String billId, String table, String bmField, String idField) throws SQLException {
        String[] sqls=new String[2];
            String querySql = String.format(query, bmField, table, idField);
            log.debug("querysql: {}", querySql);
            List<Map<String, Object>> list = getList(billId, querySql);
            if(list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
    }

    private List<Map<String, Object>> getList(String billId, String sql) {
        List<Map<String, Object>> list = manager.createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .setParameter("bxnm", billId)
                .getResultList();
        return list;
    }
}
