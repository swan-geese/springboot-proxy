package com.hongyan.study.springbootproxy.statics;

import com.hongyan.study.springbootproxy.service.SmsService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:09 PM
 * @description sms短信发送静态代理类
 */
@Slf4j
public class SmsProxy {

    private SmsService target;

    public SmsProxy(SmsService target) {
        this.target = target;
    }

    public String send(String message) {
        before();
        String result = target.send(message);
        after();
        return result;
    }

    private void before() {
        log.info("statics before invoke");
    }

    private void after() {
        log.info("statics after invoke");
    }
}
