package com.inspur.zzy.fjgx.fsex.core.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UnitHcy {


    public static JSONObject resultSetToJsonObject(ResultSet rs) throws SQLException, JSONException {
        // json对象
        JSONObject jsonObj = new JSONObject();
        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 遍历ResultSet中的每条数据
        if (rs.next()) {
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
        }
        return jsonObj;
    }


}
