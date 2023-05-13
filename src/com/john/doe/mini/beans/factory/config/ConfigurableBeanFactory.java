package com.john.doe.mini.beans.factory.config;

import com.john.doe.mini.beans.factory.BeanFactory;

/**
 * Created by JOHN_DOE on 2023/5/10.
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    
    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);
}
