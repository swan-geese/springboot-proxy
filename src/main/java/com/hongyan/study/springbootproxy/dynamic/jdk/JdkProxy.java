package com.hongyan.study.springbootproxy.dynamic.jdk;

import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zy
 * @date Created in 2023/11/27 7:02 PM
 * @description method2 jdk proxy
 */
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object bean;

    public JdkProxy(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        before();
        Object result = method.invoke(bean, args);
        after();
        return result;
    }


    private void before() {
        log.info("jdk dynamic method2 before invoke");
    }

    private void after() {
        log.info("jdk dynamic method2 after invoke");
    }
}
