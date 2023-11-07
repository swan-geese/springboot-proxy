package com.hongyan.study.springbootproxy.service.impl;

import com.hongyan.study.springbootproxy.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:07 PM
 * @description 发送短信impl
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Override
    public String send(String message) {
        log.info("发送短信：{}", message);
        return message;
    }
}
