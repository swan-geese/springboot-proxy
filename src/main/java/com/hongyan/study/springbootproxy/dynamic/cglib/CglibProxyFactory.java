package com.hongyan.study.springbootproxy.dynamic.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:51 PM
 * @description cglib 动态代理工厂类 (cglib 动态代理增强类)
 */
@Slf4j
public class CglibProxyFactory {

    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        // 创建代理类
        return enhancer.create();

    }
}
