package com.mazeltov.beanpostprocess;

import com.mazeltov.custompoxy.Benchmark;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


@Component
public class BenchmarkAnnotationMethodBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] methods = beanClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Benchmark.class)) {
                map.put(beanName, beanClass);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), ClassUtils.getAllInterfacesForClass(beanClass), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Method beanClassMethod = beanClass.getMethod(method.getName(), method.getParameterTypes());
                    if (beanClassMethod.isAnnotationPresent(Benchmark.class)) {
                        long time = System.nanoTime();
                        Object invoke = method.invoke(bean, args);
                        long afterTime = System.nanoTime();
                        System.out.println("Method from " + beanClass.getName() + " execute for " + (afterTime - time));
                        return invoke;
                    }
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
