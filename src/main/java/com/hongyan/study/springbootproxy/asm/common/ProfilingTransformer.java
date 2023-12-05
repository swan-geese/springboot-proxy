package com.hongyan.study.springbootproxy.asm.common;

import com.hongyan.study.springbootproxy.asm.v1.ChangeVisitor;
import com.hongyan.study.springbootproxy.asm.v2.ProfilingClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ProfilingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            //设置不需要注入的类
//            if (ProfilingFilter.isNotNeedInject(className)) {
//                return classfileBuffer;
//            }
            //设置需要注入的类
            if (ProfilingFilter.isNeedInject(className)) {
                return getBytes(loader, className, classfileBuffer);
            }
//            return getBytes(loader, className, classfileBuffer);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        return classfileBuffer;
    }

    private byte[] getBytes(ClassLoader loader, String className, byte[] classfileBuffer) {
        // 1. 创建 ClassReader 读入 .class 文件到内存中
        ClassReader cr = new ClassReader(classfileBuffer);
        // 2. 创建 ClassWriter 对象，将操作之后的字节码的字节数组回写
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        // 3. 创建自定义的 ClassVisitor 对象
        //方法一：v1
//        ClassVisitor cv = new ProfilingClassAdapter(cw, className);
        //方法二：v2
        ClassVisitor cv = new ChangeVisitor(cw);
        // 4. 将 ClassVisitor 对象传入 ClassReader 中
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        // 获取修改后的 class 文件对应的字节数组
        return cw.toByteArray();
    }

}
