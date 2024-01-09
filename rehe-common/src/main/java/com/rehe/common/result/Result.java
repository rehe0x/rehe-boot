package com.rehe.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author xiech
 * @description
 * @date 2024/1/9
 */
//@R("结果集")
@Getter
@Setter
@AllArgsConstructor
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -1191613448690092491L;
    private boolean successful;
    private int code;
    private String msg;
    private long timestamp;
    private T data;

    public Result() {
        this.successful = true;
        this.code = ResultCodeEnum.SUCCESSFUL.getCode();
        this.msg = ResultCodeEnum.SUCCESSFUL.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public Result(T data) {
        this.successful = true;
        this.code = ResultCodeEnum.SUCCESSFUL.getCode();
        this.msg = ResultCodeEnum.SUCCESSFUL.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Result(boolean successful, ResultCodeEnum resultCodeEnum , T data) {
        this.successful = successful;
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Result(boolean successful, ResultCodeEnum resultCodeEnum, String msg, T data) {
        this.successful = successful;
        this.code = resultCodeEnum.getCode();
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }



    public static <T> Result<T> ok(){
        return new Result<>();
    }

    public static <T> Result<T> ok(T data){
        return new Result<>(data);
    }


    public static <T> Result<T> fail(){
        return new Result<>(false,ResultCodeEnum.FAIL, null);
    }

    public static <T> Result<T> fail(String msg){
        return new Result<>(false,ResultCodeEnum.FAIL,msg, null);
    }

    public static <T> Result<T> error(){
        return new Result<>(false,ResultCodeEnum.ERROR, null);
    }

    public static <T> Result<T> error(String msg){
        return new Result<>(false,ResultCodeEnum.ERROR, msg, null);
    }

}
