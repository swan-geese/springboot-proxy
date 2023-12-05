package com.hongyan.study.springbootproxy.javassist;

import com.hongyan.study.springbootproxy.service.SmsService;
import com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 实现 javassist 方式的 aop
 */
@SpringBootTest
@Slf4j
public class JavassistProxyTest {



    /**
     * 代理 JavassistEntity 的 testList 方法
     * 实现在方法执行前后增加时间输出，打印方法调用耗时
     */
    @SneakyThrows
    @Test
    public void javassistTest() {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.hongyan.study.springbootproxy.javassist.JavassistEntity");
        CtMethod ctMethod = ctClass.getDeclaredMethod("testList");
        ctClass.addField(CtField.make("public static long cost = 0l;", ctClass));
        ctMethod.insertBefore("log.info(\"执行方法之前\"); cost = System.currentTimeMillis();");
        ctMethod.insertAfter("log.info(\"执行方法之后 cost =\" + (System.currentTimeMillis() - cost));");
        ctClass.toClass();
        JavassistEntity.testList();
    }

    /**
     * 2023-11-27 18:35:22.642  INFO 40535 --- [           main] c.h.s.s.javassist.JavassistEntity        : 执行方法之前
     * Tome
     * Jack
     * Lily
     * 2023-11-27 18:35:23.648  INFO 40535 --- [           main] c.h.s.s.javassist.JavassistEntity        : 执行方法之后 cost =1006
     */


    @SneakyThrows
    @Test
    public void proxyTest() {
        JavassitProxy proxy = new JavassitProxy(new JavassistEntity());
        JavassistEntity javassistEntityProxy = (JavassistEntity) proxy.getProxy();
        javassistEntityProxy.display();
    }

    /**
     * 2023-11-27 18:36:08.691  INFO 40800 --- [           main] c.h.s.s.javassist.JavassitProxy          : javassist before display
     * 2023-11-27 18:36:08.693  INFO 40800 --- [           main] c.h.s.s.javassist.JavassistEntity        : >>>>>>>>>>我是常量
     * 2023-11-27 18:36:08.693  INFO 40800 --- [           main] c.h.s.s.javassist.JavassistEntity        : >>>>>>>>>>我是常量
     * 2023-11-27 18:36:08.694  INFO 40800 --- [           main] c.h.s.s.javassist.JavassistEntity        : >>>>>>>>>>我是常量
     * 2023-11-27 18:36:08.694  INFO 40800 --- [           main] c.h.s.s.javassist.JavassitProxy          : javassist after display
     */

    @SneakyThrows
    @Test
    public void proxy2Test() {
        JavassitProxy proxy = new JavassitProxy(new SmsServiceImpl());
        SmsService javassistEntityProxy = (SmsService) proxy.getProxy();
        javassistEntityProxy.send("hello");
    }

    /**
     * 2023-11-27 18:46:21.403  INFO 44408 --- [           main] c.h.s.s.javassist.JavassitProxy          : javassist before send
     * 2023-11-27 18:46:21.404  INFO 44408 --- [           main] c.h.s.s.service.impl.SmsServiceImpl      : 发送短信：hello
     * 2023-11-27 18:46:21.404  INFO 44408 --- [           main] c.h.s.s.javassist.JavassitProxy          : javassist after send
     */


    @SneakyThrows
    @Test
    public void javassist2Test() {
        ClassPool pool = ClassPool.getDefault();
        // 获取接口
//        CtClass ctClassInteface = pool.get("com.hongyan.study.springbootproxy.service.SmsService");
        // 获取上面生成的类
        CtClass ctClass = pool.get("com.hongyan.study.springbootproxy.service.impl.SmsServiceImpl");
        // 使代码生成的类，实现 PersonI 接口
//        ctClass.setInterfaces(new CtClass[]{ctClassInteface});
        CtMethod ctMethod = ctClass.getDeclaredMethod("send");
        ctClass.addField(CtField.make("public static long cost = 0l;", ctClass));
        ctMethod.insertBefore("log.info(\"执行方法之前\"); cost = System.currentTimeMillis();");
        ctMethod.insertAfter("log.info(\"执行方法之后 cost =\" + (System.currentTimeMillis() - cost));");

        // 新增一个方法
        ctMethod = new CtMethod(CtClass.voidType, "joinFriend", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(\"i want to be your friend\");}");
        ctClass.addMethod(ctMethod);


        SmsService smsService = (SmsService)ctClass.toClass().newInstance();
        smsService.send("hello");

    }


}
