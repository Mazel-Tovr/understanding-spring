package com.mazeltov.beanpostprocess;

import com.mazeltov.custompoxy.Benchmark;
import com.sun.corba.se.pept.transport.ByteBufferPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class BenchmarkAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Benchmark.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if(beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    long time = System.nanoTime();
                    Object invoke = method.invoke(bean, args);
                    long afterTime = System.nanoTime();
                    System.out.println("Method from "+beanClass.getName()+" execute for "+(afterTime-time));
                    return invoke;
                }
            });
        }
        return bean;
    }
}
