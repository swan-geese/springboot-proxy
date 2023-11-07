package com.hongyan.study.springbootproxy.statics;

import com.hongyan.study.springbootproxy.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 静态代理测试类
 * 静态代理实现步骤:
 *
 * 定义一个接口及其实现类: SmsService, SmsServiceImpl
 * 创建一个代理类同样实现这个接口: SmsProxy
 * 将目标对象注注入进代理类，然后在代理类的对应方法调用目标类中的对应方法。
 * 这样的话，我们就可以通过代理类屏蔽对目标对象的访问，并且可以在目标方法执行前后做一些自己想做的事情。
 */
@SpringBootTest
@Slf4j
public class StaticsProxyTest {

    @Resource
    private SmsService smsService;

    @Test
    public void staticsTest() {
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("hello world");
    }

    /**
     * 2023-11-07 14:29:24.417  INFO 58192 --- [           main] c.h.s.springbootproxy.statics.SmsProxy   : statics before send sms
     * 2023-11-07 14:29:24.417  INFO 58192 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello world
     * 2023-11-07 14:29:24.418  INFO 58192 --- [           main] c.h.s.springbootproxy.statics.SmsProxy   : statics after send sms
     */

}
