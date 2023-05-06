package com.john.doe.mini.beans;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
