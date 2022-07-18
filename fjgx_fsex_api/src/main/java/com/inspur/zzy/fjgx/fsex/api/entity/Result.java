package com.inspur.zzy.fjgx.fsex.api.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    private boolean result;
    private int Code;
    private String Message;
    private Object value;

    public static Result success(Object value){
        Result result=new Result();
        result.setResult(true);
        result.setCode(1);
        result.setMessage("");
        result.setValue(value);
        return result;
    }
    public static Result error(String msg){
        Result result=new Result();
        result.setResult(false);
        result.setCode(0);
        result.setMessage(msg);
        result.setValue(null);
        return result;
    }
}
