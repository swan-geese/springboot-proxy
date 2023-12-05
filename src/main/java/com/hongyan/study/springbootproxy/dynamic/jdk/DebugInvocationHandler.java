package com.hongyan.study.springbootproxy.dynamic.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:23 PM
 * @description jdk动态代理处理类
 */
@Slf4j
public class DebugInvocationHandler implements InvocationHandler {

    /**
     * 被代理对象：代理类中的真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object proxy) {
        this.target = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void before() {
        log.info("jdk dynamic before invoke");
    }

    private void after() {
        log.info("jdk dynamic after invoke");
    }
}
