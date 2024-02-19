package com.pkh.annotation.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class PkhLogAspect {

    @Pointcut("@annotation(com.pkh.annotation.PkhLog)")
    public void pkhLogAspect(){}

    @Before("pkhLogAspect()")
    public void beforePkhLog(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String methodName = joinPoint.getSignature().getName();
        log.info("========================================= Method " + methodName + "() begin=========================================");
        // 执行时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d= new Date();
        String time = sdf.format(d);
        log.info("Time           : " + time);
        // 打印请求 URL
        log.info("URL            : " + request.getRequestURL());
        // 打印 请求方法
        log.info("HTTP Method    : " + request.getMethod());
        // 打印controller 的全路径以及执行方法
        log.info("Class Method   : " + joinPoint.getSignature().getDeclaringTypeName() + "." + methodName);
        // 打印请求的 IP
        log.info("IP             : " + request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : " + JSON.toJSONString(joinPoint.getArgs()));
        log.info("Executing Controller...");
    }

    @After("pkhLogAspect()")
    public void afterPkhLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("========================================= Method " + methodName + "() End =========================================");
    }
}
