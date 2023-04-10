package com.pkh.annotation.parser;

import com.pkh.annotation.Replace;

import java.lang.reflect.Field;

public class ReplaceParser {

    /**
     * 解析Replace注解
     *
     * @param o 解析对象
     */
    public static Object parse(Object o) {
        Class<?> mClass = null;
        Object o1 = o;
        try {
            mClass = o.getClass();
            Field[] fields = mClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Replace.class)) {
                    field.setAccessible(true);
                    // 获取当前field设置的注解
                    Replace trimSpace = field.getAnnotation(Replace.class);
                    String source = trimSpace.source();
                    String target = trimSpace.target();

                    // 获取当前field的值进行处理  " kangkang@pika.com "
                    String s = String.valueOf(field.get(o1));
                    String replace = s.replace(target, source);
                    field.set(o1,replace);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o1;
    }
}
