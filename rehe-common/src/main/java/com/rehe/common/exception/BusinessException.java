package com.rehe.common.exception;

import com.rehe.common.result.ResultCodeEnum;
import lombok.Getter;

import java.io.Serial;

/**
 * @author xiech
 * @description
 * @date 2024/1/10
 */
@Getter
public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 7819331324716066879L;

    private int code = ResultCodeEnum.FAIL.getCode();

    public BusinessException() {
        super();
    }
    public BusinessException(String message){
        super(message);
    }

    public BusinessException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, String message){
        super(message);
        this.code = resultCodeEnum.getCode();
    }


}
