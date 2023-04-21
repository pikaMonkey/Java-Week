package com.pkh.annotation.aspect;

import com.pkh.annotation.DS;
import com.pkh.commons.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-10)
@Component
public class DataSourceAspect {
    /**
     * 日志打印工具
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.pkh.annotation.DS)")
    public void dataSourceAspect(){}

    @Before("dataSourceAspect() && @annotation(ds)")
    public void changeDataSource(JoinPoint joinPoint, DS ds) {
        String dsId = ds.value();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            DynamicDataSourceContextHolder.setDataSourceType("pkhdb");
            logger.warn("datasource not exist, dsId:{}", dsId);
        } else {
            logger.info("change dataSource to:" + ds.value() + ". method:" + joinPoint.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(ds.value());
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DS ds) {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
