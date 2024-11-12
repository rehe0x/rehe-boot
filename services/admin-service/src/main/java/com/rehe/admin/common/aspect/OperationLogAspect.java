package com.rehe.admin.common.aspect;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rehe.admin.AdminServiceApplication;
import com.rehe.admin.modules.logging.dto.reqeust.OperationLogCreateDto;
import com.rehe.admin.modules.logging.service.OperationLogService;
import com.rehe.common.log.OperationLog;
import com.rehe.common.util.RequestUtils;
import com.rehe.common.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiech
 * @description
 * @date 2024/11/11
 */
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {
    private static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class.getName());

    private final OperationLogService operationLogService;

    @Pointcut("@annotation(com.rehe.common.log.OperationLog)")
    public void logPointcut() {}

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 执行目标方法
        Object result = joinPoint.proceed();
        insertLog(startTime, joinPoint,"info","");
        return result;
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        long startTime = System.currentTimeMillis();
        insertLog(startTime, joinPoint,"error",ex.getMessage());
    }

    private void insertLog(long startTime,JoinPoint joinPoint,String type,String detail){
        // 后置逻辑，例如记录执行时间
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        HttpServletRequest httpServletRequest = RequestUtils.getHttpServletRequest();

        OperationLogCreateDto operationLogCreateDto = new OperationLogCreateDto();
        operationLogCreateDto.setUsername(SecurityUtils.getLoginUsername());
        operationLogCreateDto.setDescription(operationLog.value());
        operationLogCreateDto.setLogType(type);
        operationLogCreateDto.setMethod(joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()");
        operationLogCreateDto.setParams(getParameter(method, joinPoint.getArgs()));
        operationLogCreateDto.setIp(RequestUtils.getIp(httpServletRequest));
        operationLogCreateDto.setAddress("");
        operationLogCreateDto.setHeader(RequestUtils.getBrowser(httpServletRequest));
        operationLogCreateDto.setTime(System.currentTimeMillis() - startTime);
        operationLogCreateDto.setDetail(detail);
        operationLogService.createOperationLog(operationLogCreateDto);
    }

    private String getParameter(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Map<String,Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            paramMap.put(parameters[i].getName(), args[i]);
        }
        return JSONObject.toJSONString(paramMap);
    }
}
