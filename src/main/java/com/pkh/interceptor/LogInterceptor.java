package com.pkh.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final static String MDC_TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Interceptor PreHandle event");
//        long currentTime = System.nanoTime();
//        String traceIdStr = String.join("_", MDC_TRACE_ID, String.valueOf(currentTime));
//        log.info("LogInterceptor traceId: {}", traceIdStr);
//        MDC.put(MDC_TRACE_ID, traceIdStr);
//        log.info("LogInterceptor Put MDC Successfully!");
        return true;
    }
}
