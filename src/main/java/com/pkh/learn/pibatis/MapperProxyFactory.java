package com.pkh.learn.pibatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MapperProxyFactory {

    public static <T> T getMapper(Class<T> mapper) {
        Object mapperProxy = Proxy.newProxyInstance(MapperProxyFactory.class.getClassLoader(), new Class[]{mapper}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
        return (T) mapperProxy;
    }
}
