package com.hongyan.study.springbootproxy.dynamic.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:48 PM
 * @description cglib动态代理：自定义MethodInterceptor
 * 此 MethodInterceptor 为 cglib 的拦截器，用于拦截 Service 层的方法， 并非 AspectJ 的 MethodInterceptor
 * AspectJ MethodInterceptor : org.aopalliance.intercept.MethodInterceptor
 * cglib MethodInterceptor : net.sf.cglib.proxy.MethodInterceptor
 */

@Slf4j
public class DebugMethodInterceptor implements MethodInterceptor {


    /**
     * @param o           被代理的对象（需要增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param args        方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object target = methodProxy.invokeSuper(o, args);
        after();

        return target;
    }


    private void before() {
        log.info("cglib dynamic before invoke");
    }

    private void after() {
        log.info("cglib dynamic after invoke");
    }
}
