package com.jim.spring.boot.spider.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jim lin
 * 2018/9/13.
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public static void setContext(ApplicationContext contextinject) {
        if (context != null) {
            return;
        }
        context = contextinject;
    }

    public static Object getBeanByName(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}