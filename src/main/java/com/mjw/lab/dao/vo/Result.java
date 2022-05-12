package com.mjw.lab.dao.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String mes;

    private Object data;

    public static com.mjw.lab.dao.vo.Result success(Object data){
        return new com.mjw.lab.dao.vo.Result(true,200,"success",data);
    }

    public static com.mjw.lab.dao.vo.Result fail(int code, String msg){
        return new com.mjw.lab.dao.vo.Result(false,code,msg,null);
    }
}
