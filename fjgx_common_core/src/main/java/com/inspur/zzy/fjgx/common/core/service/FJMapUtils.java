package com.inspur.zzy.fjgx.common.core.service;

import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class FJMapUtils {
    public static void setFieldByMap(Map mapping, Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (String.class.isAssignableFrom(field.getType())){
                try {
                    field.setAccessible(true);
                    //如果字段是空才赋值
                    if(Objects.isNull(field.get(o))){
                        //mapping.keySet().
                        field.set(o, Objects.nonNull(mapping.get(field.getName()))?mapping.get(field.getName()).toString():"");
                    }
                } catch (IllegalAccessException e){
                    //log.error("SetFieldByMap方法异常",e);
                    throw new RuntimeException("SetFieldByMap 方法异常");
                }
            }
        }
    }
}
