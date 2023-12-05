package com.hongyan.study.springbootproxy.dynamic.cglib;

import com.hongyan.study.springbootproxy.service.SmsService;
import com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class CglibProxyTest {

    @Resource
    private SmsService smsService;


    @Test
    public void cglibDynamicTest() {
        SmsService smsServiceProxy = (SmsService)CglibProxyFactory.getProxy(smsService.getClass());
        smsServiceProxy.send("hello");
    }

    /**
     * 2023-11-07 15:01:09.323  INFO 69569 --- [           main] c.h.s.s.d.cglib.DebugMethodInterceptor   : cglib dynamic before send sms
     * 2023-11-07 15:01:09.332  INFO 69569 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 2023-11-07 15:01:09.334  INFO 69569 --- [           main] c.h.s.s.d.cglib.DebugMethodInterceptor   : cglib dynamic after send sms
     */

    @Test
    public void cglibDynamicMethod2Test() {
        CglibProxy cglibProxy = new CglibProxy(new SmsServiceImpl("Tom"));
        SmsService smsServiceProxy = (SmsService)cglibProxy.getProxy();
        smsServiceProxy.send();
    }

    /**
     * 2023-12-04 17:20:34.551  INFO 40355 --- [           main] c.h.s.s.dynamic.cglib.CglibProxy         : cglib dynamic method2 before invoke
     * 2023-12-04 17:20:34.551  INFO 40355 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：Tom
     * 2023-12-04 17:20:34.552  INFO 40355 --- [           main] c.h.s.s.dynamic.cglib.CglibProxy         : cglib dynamic method2 after invoke
     */

}
