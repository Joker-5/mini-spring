package com.john.doe.mini.beans.factory.config;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.BeanFactory;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
    
    void setBeanFactory(BeanFactory beanFactory);
}
