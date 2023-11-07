# SpringBoot 实现 Java 代理

### 静态代理
定义一个接口及其实现类；SmsService、SmsServiceImpl
创建一个代理类同样实现这个接口：SmsProxy
将目标对象注注入进代理类，然后在代理类的对应方法调用目标类中的对应方法。这样的话，我们就可以通过代理类屏蔽对目标对象的访问，并且可以在目标方法执行前后做一些自己想做的事情。


### JDK 动态代理
定义一个接口及其实现类；SmsService、SmsServiceImpl
自定义 InvocationHandler 并重写invoke方法，在 invoke 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑；DebugInvocationHandler
通过 Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) 方法创建代理对象；JdkProxyFactory


### CGLIB 动态代理
定义一个接口及其实现类；SmsService、SmsServiceImpl
自定义 MethodInterceptor 并重写 intercept 方法，intercept 用于拦截增强被代理类的方法，和 JDK 动态代理中的 invoke 方法类似；DebugMethodInterceptor
通过 Enhancer 类的 create()创建代理类；CglibProxyFactory

