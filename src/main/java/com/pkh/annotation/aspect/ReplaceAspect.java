package com.pkh.annotation.aspect;

import com.pkh.annotation.Replace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Order(1)
@Component
@Slf4j
public class ReplaceAspect {
    /**
     * 定义切点，@Replace为切点
     */
    @Pointcut("@within(com.pkh.annotation.Replace)")
    public void replaceAspect() {}

    /**
     * 切入点之前执行
     */
    @Around("replaceAspect()")
    public void parseReplace(JoinPoint joinPoint) {
//        System.out.println("parseReplace before");
        log.info("-----------around--------------");
        Object o = joinPoint.getTarget();
        Class<?> clazz = o.getClass();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Replace.class)) {
                    field.setAccessible(true);
                    // 获取当前field设置的注解
                    Replace trimSpace = field.getAnnotation(Replace.class);
                    String source = trimSpace.source();
                    String target = trimSpace.target();

                    // 获取当前field的值进行处理  " kangkang@pika.com "
                    String s = String.valueOf(field.get(o));
                    String replace = s.replace(target, source);
                    field.set(o,replace);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
