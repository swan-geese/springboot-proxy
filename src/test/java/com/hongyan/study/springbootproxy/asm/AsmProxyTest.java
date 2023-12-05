package com.hongyan.study.springbootproxy.asm;

import com.hongyan.study.springbootproxy.SpringbootProxyApplication;
import com.hongyan.study.springbootproxy.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class AsmProxyTest {

    @Resource
    private SmsService smsService;

    /**
     * asm 代理测试
     * jvm启动参数：-noverify -javaagent:/Users/dearzhang/info/git-work/java-work/springboot-proxy/target/springboot-proxy-0.0.1-SNAPSHOT.jar
     */
    @Test
    public void asmProxyTest() {
        smsService.send("hello");
    }

    /**
     * 2023-12-05 11:07:29.818  INFO 18750 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * The cost time of asmProxyTest() is 1 ms
     */

}
