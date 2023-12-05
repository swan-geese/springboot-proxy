package com.hongyan.study.springbootproxy.dynamic.jdk;

import com.hongyan.study.springbootproxy.service.SmsService;
import com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理测试类
 *
 * 定义一个接口及其实现类；SmsService, SmsServiceImpl
 * 自定义 InvocationHandler 并重写invoke方法，在 invoke 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑； DebugInvocationHandler
 * 通过 Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) 方法创建代理对象； JdkProxyFactory
 *
 */
@SpringBootTest
@Slf4j
public class JdkProxyTest {

    @Resource
    private SmsService smsService;
    @Test
    public void jdkDynamicTest() {
        SmsService smsServiceProxy = (SmsService)JdkProxyFactory.getProxy(smsService);
        smsServiceProxy.send("hello");
    }

    /**
     * 2023-11-07 14:40:55.101  INFO 62306 --- [           main] c.h.s.s.d.jdk.DeubgInvocationHandler     : jdk dynamic before send sms
     * 2023-11-07 14:40:55.102  INFO 62306 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 2023-11-07 14:40:55.103  INFO 62306 --- [           main] c.h.s.s.d.jdk.DeubgInvocationHandler     : jdk dynamic after send sms
     */

    @Test
    public void jdkDynamicMethod2Test() {
        JdkProxy jdkProxy = new JdkProxy(new SmsServiceImpl());
        SmsService smsService = (SmsService)Proxy.newProxyInstance(jdkProxy.getClass().getClassLoader(), new Class[]{SmsService.class}, jdkProxy);
        smsService.send("hello");

    }

    /**
     * 2023-12-04 17:20:03.832  INFO 40203 --- [           main] c.h.s.s.dynamic.jdk.JdkProxy             : jdk dynamic method2 before invoke
     * 2023-12-04 17:20:03.832  INFO 40203 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 2023-12-04 17:20:03.833  INFO 40203 --- [           main] c.h.s.s.dynamic.jdk.JdkProxy             : jdk dynamic method2 after invoke
     */

}

