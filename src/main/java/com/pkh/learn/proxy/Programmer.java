package com.pkh.learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Programmer implements Coder {
    @Override
    public void coding() {
        System.out.println("programmer is coding");
    }

    public static void main(String[] args) {
        Programmer programmer = new Programmer();
        Coder coderProxy = (Coder) Proxy.newProxyInstance(Programmer.class.getClassLoader(), new Class[]{Coder.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("方法：" + method.getName() + "()执行前");
                Object invoke = method.invoke(programmer, args);
                System.out.println("方法：" + method.getName() + "()执行后");
                return invoke;
            }
        });
        coderProxy.coding();
    }
}
