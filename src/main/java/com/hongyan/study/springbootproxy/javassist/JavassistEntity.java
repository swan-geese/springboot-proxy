package com.hongyan.study.springbootproxy.javassist;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JavassistEntity {

    // 声明 一个常量
    public static final String FLAG = "我是常量";

    // 普通方法
    public void display(){
        for (int i = 0; i < 3; i++) {
            log.info(">>>>>>>>>>" + FLAG);
        }
    }

    // 带有List返回值
    public static List<String> testList(){
        List<String> list = new ArrayList<>();
        list.add("Tome");
        list.add("Jack");
        list.add("Lily");
        try {
            Thread.sleep(1002L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.stream().forEach(System.out::println);
        return list;
    }
}