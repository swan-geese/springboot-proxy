package com.hongyan.study.springbootproxy.bytebuddy;

import com.hongyan.study.springbootproxy.service.SmsService;
import com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class ByteBuddyProxyTest {

    @Resource
    private SmsService smsService;

    /**
     * ByteBuddyProxy 代理测试
     */
    @SneakyThrows
    @Test
    public void byteBuddyProxyTest() {
        ByteBuddyProxy byteBuddyProxy = new ByteBuddyProxy(new SmsServiceImpl());
        SmsService smsService = (SmsService)byteBuddyProxy.getProxy();
        smsService.send("hello");
    }

    /**
     * 2023-12-04 17:21:14.781  INFO 40592 --- [           main] c.h.s.s.bytebuddy.ByteBuddyProxy         : Bytebuddy before invoke send
     * 2023-12-04 17:21:14.782  INFO 40592 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 2023-12-04 17:21:14.782  INFO 40592 --- [           main] c.h.s.s.bytebuddy.ByteBuddyProxy         : Bytebuddy after invoke send
     */

    /**
     * ByteBuddyProxy 代理测试2
     * jvm启动参数：-javaagent:D:\workspace\springboot-proxy\target\springboot-proxy-0.0.1-SNAPSHOT.jar=com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl
     * -javaagent:/Users/dearzhang/info/git-work/java-work/springboot-proxy/target/springboot-proxy-0.0.1-SNAPSHOT.jar
     */
    @SneakyThrows
    @Test
    public void byteBuddyProxy2Test() {
        smsService.send("hello");
    }

    /**
     * 监控 - Agent Begin
     * 方法全路径：com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl.send
     * 方法名称：send(java.lang.String)
     *     入参个数：1
     *     出参类型：java.lang.String
     *     出参结果：hello
     * 方法耗时：5ms
     * 监控 - Agent End
     *
     * 监控 - Agent Begin
     * 方法全路径：com.hongyan.study.springbootproxy.bytebuddy.ByteBuddyProxyTest.byteBuddyProxy2Test
     * 方法名称：byteBuddyProxy2Test
     *     入参个数：0
     *     出参类型：void
     *     出参结果：null
     * 方法耗时：6ms
     * 监控 - Agent End
     */




}
