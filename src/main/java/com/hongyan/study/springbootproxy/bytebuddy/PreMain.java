package com.hongyan.study.springbootproxy.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import java.lang.instrument.Instrumentation;

@Slf4j
public class PreMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("======================= premain start =======================");
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule, domain) -> {
            return builder
                    .method(ElementMatchers.any()      // 拦截任意方法
//                    .method(ElementMatchers.named("process")
//                            .or(ElementMatchers.named("setSupplyContextBatchNum"))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("premain")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("getClass")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("hashCode")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("equals")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("clone")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("toString")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("notify")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("notifyAll")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("wait")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("finalize")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("afterPropertiesSet")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("get")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("set")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("is")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("values")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("init")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("currentMapperClass")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("currentModelClass")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("CGLIB$")))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("loadTemplate")))
                    ) // 拦截任意方法
                    .intercept(MethodDelegation.to(MonitorMethod.class)); // 委托
        };

        new AgentBuilder
                .Default()
//                .type(ElementMatchers.nameStartsWith(agentArgs)) // 指定需要拦截的类
//                .type(ElementMatchers.any()) // 指定需要拦截的类
//                .type(ElementMatchers.nameStartsWith("com.paraview.idm")) // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("com.hongyan.study.springbootproxy")) // 指定需要拦截的类
                .transform(transformer)
                .installOn(inst);
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
        log.info("======================= premain start =======================");
    }

}
