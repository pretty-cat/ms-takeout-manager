package com.qf.redisCache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 要想拿到ApplicationContext, 需要是实现ApplicationContextAware， 然后实现
 * 它里面的 setApplicationContext() 方法, 当项目启动时候，扫描到某个实现了
 * ApplicationContextAware, 会自动的调用 这个实现类中重写的方法。并且会将
 * ApplicationContext的实例，丢给方法。
 */

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
        this.applicationContext = _applicationContext;
    }

    // 根据bean的名字获取容器中的bean
    public static <T> T getBean(String name) {
        return (T)applicationContext.getBean(name);
    }
}
