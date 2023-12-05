package com.hongyan.study.springbootproxy.dynamic.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/27 6:52 PM
 * @description method2 cglib proxy
 */
@Slf4j
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    private Object bean;

    public CglibProxy(Object bean) {
        this.bean = bean;
    }

    public Object getProxy(){
        //设置需要创建子类的类
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    /**
     * 实现MethodInterceptor接口方法
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String methodName = method.getName();
        before();
        //通过代理类调用父类中的方法
        //return proxy.invokeSuper(obj, args);

        //调用原bean的方法
        Object result = method.invoke(bean,args);
        after();
        return result;
    }



    private void before() {
        log.info("cglib dynamic method2 before invoke");
    }

    private void after() {
        log.info("cglib dynamic method2 after invoke");
    }
}
