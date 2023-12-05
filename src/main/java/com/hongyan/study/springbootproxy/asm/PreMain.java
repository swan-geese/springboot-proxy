package com.hongyan.study.springbootproxy.asm;

import com.hongyan.study.springbootproxy.asm.common.ProfilingTransformer;
import lombok.extern.slf4j.Slf4j;
import java.lang.instrument.Instrumentation;

@Slf4j
public class PreMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("======================= premain start =======================");
        inst.addTransformer(new ProfilingTransformer());

    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
        log.info("======================= premain start =======================");
    }

}
