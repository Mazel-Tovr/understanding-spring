package com.mazeltov.listner;

import com.mazeltov.postcontext.AfterReadyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

public class ApplicationContextReadyListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalName = beanDefinition.getBeanClassName();
            try {
                Class<?> originalClass = Class.forName(originalName);
                Method[] methods = originalClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AfterReadyContext.class)) {
                        Object currentBean = context.getBean(name);
                        Method currentMethod = currentBean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentMethod.invoke(currentBean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
