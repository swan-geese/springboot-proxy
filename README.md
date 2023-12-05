# SpringBoot 实现 Java 代理

### 静态代理
1. 定义一个接口及其实现类：SmsService、SmsServiceImpl
2. 创建一个代理类同样实现这个接口：SmsProxy
3. 将目标对象注注入进代理类，然后在代理类的对应方法调用目标类中的对应方法。这样的话，我们就可以通过代理类屏蔽对目标对象的访问，并且可以在目标方法执行前后做一些自己想做的事情。
4. 测试类：StaticProxyTest

### AOP 动态代理
1. 定义一个自定义注解：@DoMonitor
2. 定义一个切面类，用于环绕通知含 @DoMonitor 相关处理事件：MonitorAspect
3. 定义一个 config 配置类，用于加载切面类 Bean： MonitorAutoConfigure
4. 将该自定义注解作用到需要被代理的方法上：SmsServiceImpl
```java
@DoMonitor(key = "sms", desc = "发送短信")
public String send(String message) {
    log.info("发送短信：{}", message);
    return message;
}
```
4. 测试类：AopProxyTest#aopProxyTest

### JDK 动态代理
1. 定义一个接口及其实现类：SmsService、SmsServiceImpl
2. 自定义 InvocationHandler 并重写invoke方法，在 invoke 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑；DebugInvocationHandler
3. 通过 Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) 方法创建代理对象；JdkProxyFactory
4. 测试类：JdkProxyTest

### CGLIB 动态代理
1. 引入 cglib 依赖
```xml
<!-- cglib 动态代理所需 jar-->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
```
2. 定义一个接口及其实现类：SmsService、SmsServiceImpl
3. 自定义 MethodInterceptor 并重写 intercept 方法，intercept 用于拦截增强被代理类的方法，和 JDK 动态代理中的 invoke 方法类似；DebugMethodInterceptor
4. 通过 Enhancer 类的 create()创建代理类；CglibProxyFactory
5. 测试类：CglibProxyTest

### AscpectJ 动态代理
1. 引入 aspectjweaver 依赖
```xml
<!-- aspectj 代理所需 jar-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.12</version>
</dependency>
```
2. 定义一个接口及其实现类：SmsService、SmsServiceImpl
3. 自定义 MethodInterceptor 并重写 invoke 方法，invoke 用于拦截增强被代理类的方法，和 JDK 动态代理中的 invoke 方法类似；ServiceInterceptor（注意： 此 MethodInterceptor 非彼 MethodInterceptor）
4. 通过 ApplicationContext.getBean() 来调用代理类
5. 测试类：AspectJproxyTest

### Javassist 动态代理
1. 引入 Javassist 依赖
```xml
<!-- javassist 代理所需 jar-->
<dependency>
    <groupId>org.javassist</groupId>
    <artifactId>javassist</artifactId>
    <version>3.29.2-GA</version>
</dependency>
```
2. 定义一个需被增强的业务类：JavassistEntity
3. 通过 ProxyFactory.createClass() 来创建代理类的 Class 对象，然后创建代理类的实例：JavassistProxy
4. 测试类：JavassistProxyTest


### ByteBuddy 动态代理
1. 引入 ByteBuddy 依赖
```xml
<!-- bytebuddy 代理所需 jar-->
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy</artifactId>
    <version>1.14.5</version>
</dependency>
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy-agent</artifactId>
    <version>1.14.5</version>
</dependency>
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy-maven-plugin</artifactId>
    <version>1.14.5</version>
</dependency>
```
2. 定义一个接口及其实现类：SmsService、SmsServiceImpl
3. 自定义 AopInvocationHandler 并重写 invoke 方法，实现 InvocationHandler 接口；AopInvocationHandler
3. 通过 new ByteBuddy().subclass() 来创建代理类的 Class 对象，然后创建代理类的实例：ByteBuddyProxy
4. 测试类：ByteBuddyProxyTest#byteBuddyProxyTest

### ByteBuddy 动态代理插件模式（Agent）
1. 引入 ByteBuddy 依赖
```xml
<!-- bytebuddy 代理所需 jar-->
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy</artifactId>
    <version>1.14.5</version>
</dependency>
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy-agent</artifactId>
    <version>1.14.5</version>
</dependency>
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy-maven-plugin</artifactId>
    <version>1.14.5</version>
</dependency>
```

2. 自定义MANIFEST.MF，增加预启动类：com.hongyan.study.springbootproxy.bytebuddy.monitor.PreMain
```xml
<properties>
    <java.version>11</java.version>
    <!-- 自定义MANIFEST.MF -->
    <maven.configuration.manifestFile>src/main/resources/META-INF/MANIFEST.MF</maven.configuration.manifestFile>
</properties>
<!-- 将byte-buddy打包到Agent中 -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <artifactSet>
            <includes>
                <include>net.bytebuddy:byte-buddy:jar:</include>
                <include>net.bytebuddy:byte-buddy-agent:jar:</include>
            </includes>
        </artifactSet>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>2.4</version>
<configuration>
    <archive>
        <manifestFile>${maven.configuration.manifestFile}</manifestFile>
    </archive>
</configuration>
</plugin>
```

3. 预启动类中定义拦截方法、拦截包路径等：com.hongyan.study.springbootproxy.bytebuddy.monitor.PreMain
```java

public class PreMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain start");
        inst.addTransformer(new Transformer(), true);
    }
}
```

4. 自定义MANIFEST.MF文件内容
```xml
Manifest-Version: 1.0
Premain-Class: com.hongyan.study.springbootproxy.bytebuddy.monitor.PreMain
Can-Redefine-Classes: true
```

5. 定义一个接口及其实现类：SmsService、SmsServiceImpl
6. 实现一个 MonitorMethod 类，监控方法入参，出参，执行时间等信息，例：MonitorMethod
7. 测试类：ByteBuddyProxyTest#byteBuddyProxy2Test

### Asm 动态代理
1. 引入 Asm 依赖
```xml
<!--asm 代理所需 jar-->
<dependency>
    <groupId>org.ow2.asm</groupId>
    <artifactId>asm-commons</artifactId>
    <version>9.2</version>
</dependency>
```

2. 自定义MANIFEST.MF，增加预启动类：com.hongyan.study.springbootproxy.asm.PreMain
```xml
<properties>
    <java.version>11</java.version>
    <!-- 自定义MANIFEST.MF -->
    <maven.configuration.manifestFile>src/main/resources/META-INF/MANIFEST.MF</maven.configuration.manifestFile>
</properties>
<!-- 将byte-buddy打包到Agent中 -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <artifactSet>
            <includes>
                <include>net.bytebuddy:byte-buddy:jar:</include>
                <include>net.bytebuddy:byte-buddy-agent:jar:</include>
            </includes>
        </artifactSet>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>2.4</version>
<configuration>
    <archive>
        <manifestFile>${maven.configuration.manifestFile}</manifestFile>
    </archive>
</configuration>
</plugin>
```

3. 定义一个 transformer 类，设置类注入规则：ProfilingTransformer
4. 创建自定义的 ClassVisitor 对象，设置方法增强 ：ChangeVisitor
5. 测试类：AsmProxyTest#asmProxyTest
