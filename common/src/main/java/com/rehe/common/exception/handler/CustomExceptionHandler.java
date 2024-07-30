package com.rehe.common.exception.handler;

import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.HttpError;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author xiech
 * @description 统一异常处理
 * @date 2024/1/10
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Throwable.class)
    public Result<String> handleException(Throwable e){
        e.printStackTrace();
        return Result.fail(ResultCodeEnum.ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result<String> noResourceFoundException(NoResourceFoundException e){
        return Result.fail(ResultCodeEnum.ERROR.getCode(),e.getMessage());
    }

    /**
     * 参数校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e){
        return Result.fail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result<String> handlerMethodValidationException(HandlerMethodValidationException e){
        return Result.fail(Objects.requireNonNull(e.getAllErrors().get(0).getDefaultMessage()));
    }



    @ExceptionHandler(BusinessException.class)
    public Result<String> businessException(BusinessException e){
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public Result<String> badCredentialsException(BadCredentialsException e){
        return Result.fail("用户名或密码不正确");
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public Result<String> authorizationDeniedException(AuthorizationDeniedException e){
        return Result.fail(ResultCodeEnum.NO_AUTHORITY);
    }



}
