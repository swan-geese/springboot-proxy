package com.hongyan.study.springbootproxy.service;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/7 2:06 PM
 * @description 定义发送短信的接口
 */
public interface SmsService {

    String send(String message);

    String send();
}
