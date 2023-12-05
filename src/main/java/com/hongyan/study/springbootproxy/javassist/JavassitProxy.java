package com.hongyan.study.springbootproxy.javassist;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/27 6:27 PM
 * @description javassist proxy
 */
@Slf4j
public class JavassitProxy {

    private Object bean;

    public JavassitProxy(Object bean) {
        this.bean = bean;
    }

    @SneakyThrows
    public Object getProxy() throws IllegalAccessException, InstantiationException {
        ProxyFactory f = new ProxyFactory();
        f.setSuperclass(bean.getClass());

        Class c = f.createClass();
        MethodHandler mi = (self, method, proceed, args) -> {
            String methodName = method.getName();
            log.info("javassist before {}", methodName);
            Object result = method.invoke(bean, args);
            log.info("javassist after {}", methodName);
            return result;
        };
        Object proxy = c.getConstructor().newInstance();
        ((Proxy)proxy).setHandler(mi);
        return proxy;
    }
}
