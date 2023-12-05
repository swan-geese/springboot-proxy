package com.hongyan.study.springbootproxy.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 此 MethodInterceptor 为 AspectJ 的拦截器，用于拦截 Service 层的方法， 并非 cglib 的 MethodInterceptor
 * AspectJ MethodInterceptor : org.aopalliance.intercept.MethodInterceptor
 * cglib MethodInterceptor : net.sf.cglib.proxy.MethodInterceptor
 * 目前验证 cglib 代理时会跟 aspectj 代理冲突，怀疑是因为两者都是MethodInterceptor，导致冲突，暂时未找到解决方案
 */
@Slf4j
public class ServiceInterceptor implements MethodInterceptor {
    public ServiceInterceptor() {

    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        before();
        long start = System.currentTimeMillis();
        Object result = methodInvocation.proceed();
        long end = System.currentTimeMillis();
        log.info("AOP 拦截 SERVICE 花费时间: " + (end - start) + " ms");
        after();
        return result;
    }

    private void before() {
        log.info("aspectj before invoke");
    }

    private void after() {
        log.info("aspectj after invoke");
    }
}
