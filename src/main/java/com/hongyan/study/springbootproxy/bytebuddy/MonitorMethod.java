package com.hongyan.study.springbootproxy.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @description: 监控方法入参，出参，执行时间等信息
 */
public class MonitorMethod {

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable, @AllArguments Object[] args) throws Exception {
        long start = System.currentTimeMillis();
        Object resObj = null;
        try {
            resObj = callable.call();
            return resObj;
        } finally {
            System.out.println("监控 - Agent Begin");
            System.out.println("方法全路径：" + method.getDeclaringClass().getName() + "." + method.getName());
            StringBuilder sb = new StringBuilder("方法名称：" +method.getName());
            for (int i = 0; i < method.getParameterCount(); i++) {
                sb.append(i == 0 ? "(" : "");
                sb.append(method.getParameterTypes()[i].getTypeName());
                sb.append(i == (method.getParameterCount() - 1) ? ")" : ", ");
//                System.out.println("    入参 Idx：" + (i + 1) + " 类型：" + method.getParameterTypes()[i].getTypeName() + " 内容：" + args[i]);
            }
            System.out.println(sb.toString());
            System.out.println("    入参个数：" + method.getParameterCount());
            System.out.println("    出参类型：" + method.getReturnType().getName());
            System.out.println("    出参结果：" + resObj);
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("监控 - Agent End\r\n");
        }
    }

}
