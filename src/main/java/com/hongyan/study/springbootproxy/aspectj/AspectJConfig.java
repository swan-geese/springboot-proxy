package com.hongyan.study.springbootproxy.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zy
 * @version 1.0
 * @date Created in 2023/11/24 4:03 PM
 * @description AspectConfig配置
 * AspectJExpressionPointcutAdvisor : Spring AOP Advisor，可用于任何 AspectJ 切入点表达式
 * 目前验证 cglib 代理时会跟 aspectj 代理冲突，怀疑是因为两者都是MethodInterceptor，导致冲突，暂时未找到解决方案
 * 在验证 cglib 代理时，注释掉此处 AspectJConfig的 @Configuration 配置即可，反之亦然
 */

//@Configuration
@Slf4j
public class AspectJConfig {


    /**
     * 定义切面
     * 此处可以按照具体项目的包结构进行配置，例如对controller、service、dao层做aop切面，进行对应接口耗时输出等
     * @return
     */
    @Bean
    public AspectJExpressionPointcutAdvisor webInterceptor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(public * com.hongyan.study.springbootproxy.service..*(..))");
        //method1
        advisor.setAdvice(new ServiceInterceptor());
        //method2
//        advisor.setAdvice((MethodInterceptor) (methodInvocation) -> {
//            log.info("aspectj method2 before send sms");
//            Object result = methodInvocation.proceed();
//            log.info("aspectj method2 after send sms");
//            return result;
//        });
        return advisor;
    }
}
