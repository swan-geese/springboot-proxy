package com.hongyan.study.springbootproxy.dynamic.jdk;

import java.lang.reflect.Proxy;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:37 PM
 * @description jdk动态代理工厂类
 */
public class JdkProxyFactory {

    /**
     * 通过Proxy 类的 newProxyInstance() 创建的代理对象在调用方法的时候，实际会调用到实现InvocationHandler 接口的类的 invoke()方法。
     * 你可以在 invoke() 方法中自定义处理逻辑，比如在方法执行前后做什么事情。
     * @param target
     * @return
     */
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),                        // 代理需要实现的接口，可指定多个
                new DebugInvocationHandler(target));                      // 代理对象对应的自定义 InvocationHandler
    }
}
