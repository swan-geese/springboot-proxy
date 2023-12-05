package com.hongyan.study.springbootproxy.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/28 3:54 PM
 * @description ByteBuddy代理类
 */
@Slf4j
public class ByteBuddyProxy {

    private Object bean;

    public ByteBuddyProxy(Object bean) {
        this.bean = bean;
    }

    public Object getProxy() throws Exception {
        Object object = new ByteBuddy().subclass(bean.getClass())                       //创建代理类
                .method(ElementMatchers.any())                                          //监控所有方法
                .intercept(InvocationHandlerAdapter.of(new AopInvocationHandler(bean))) //拦截器
                .make()                                                                 //创建
                .load(ByteBuddyProxy.class.getClassLoader())                            //加载
                .getLoaded()
                .getConstructor() //获取加载后的class
                .newInstance();                                                         //创建实例
        return object;
    }

    public class AopInvocationHandler implements InvocationHandler {

        private Object bean;

        public AopInvocationHandler(Object bean) {
            this.bean = bean;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            before(methodName);
            Object result =  method.invoke(bean, args);
            after(methodName);
            return result;
        }

        private void before(String methodName) {
            log.info("Bytebuddy before invoke {}", methodName);
        }

        private void after(String methodName) {
            log.info("Bytebuddy after invoke {}", methodName);
        }
    }
}
