package com.rehe.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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

    protected Result() {
        this.successful = true;
        this.code = ResultCodeEnum.SUCCESSFUL.getCode();
        this.msg = ResultCodeEnum.SUCCESSFUL.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    protected Result(T data) {
        this.successful = true;
        this.code = ResultCodeEnum.SUCCESSFUL.getCode();
        this.msg = ResultCodeEnum.SUCCESSFUL.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    private Result(boolean successful, ResultCodeEnum resultCodeEnum , T data) {
        this.successful = successful;
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    private Result(boolean successful, ResultCodeEnum resultCodeEnum, String msg, T data) {
        this.successful = successful;
        this.code = resultCodeEnum.getCode();
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    private Result(boolean successful, int code, String msg, T data) {
        this.successful = successful;
        this.code = code;
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

    public static <T> Result<T> fail(int code,String msg){
        return new Result<>(false,code,msg, null);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum){
        return new Result<>(false,resultCodeEnum, null);
    }

    public static <T> Result<T> error(){
        return new Result<>(false,ResultCodeEnum.ERROR, null);
    }

    public static <T> Result<T> error(String msg){
        return new Result<>(false,ResultCodeEnum.ERROR, msg, null);
    }

}
