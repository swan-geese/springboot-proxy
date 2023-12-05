package com.hongyan.study.springbootproxy.aop;

import com.hongyan.study.springbootproxy.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class AopProxyTest {

    @Resource
    private SmsService smsService;

    /**
     * aop 代理测试
     */
    @Test
    public void aopProxyTest() {
        smsService.send("hello");
    }

    /**
     * 2023-12-04 19:19:22.916  INFO 82456 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 监控 - Begin By AOP
     * 监控索引：sms
     * 监控描述：发送短信
     * 方法名称：send
     * 方法耗时：13ms
     * 监控 - End
     */

}
