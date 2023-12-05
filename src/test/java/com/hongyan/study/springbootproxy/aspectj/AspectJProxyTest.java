package com.hongyan.study.springbootproxy.aspectj;

import com.hongyan.study.springbootproxy.SpringbootProxyApplication;
import com.hongyan.study.springbootproxy.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@Slf4j
public class AspectJProxyTest {

    /**
     * 由于 aspectj 配置与 cglib 配置冲突，所以此处注释掉 AspectJConfig 的 @Configuration 配置，需验证aspectj时，取消注释
     */
    @Test
    public void aspectJTest() {
        ApplicationContext context = SpringApplication.run(SpringbootProxyApplication.class);
        context.getBean(SmsService.class).send("hello");
    }

    /**
     * 2023-11-24 16:27:54.688  INFO 48296 --- [           main] c.h.s.s.aspectj.ServiceInterceptor       : aspectj before send sms
     * 2023-11-24 16:27:54.696  INFO 48296 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 2023-11-24 16:27:54.698  INFO 48296 --- [           main] c.h.s.s.aspectj.ServiceInterceptor       : AOP 拦截 SERVICE 花费时间: 9 ms
     * 2023-11-24 16:27:54.698  INFO 48296 --- [           main] c.h.s.s.aspectj.ServiceInterceptor       : aspectj after send sms
     */

}
