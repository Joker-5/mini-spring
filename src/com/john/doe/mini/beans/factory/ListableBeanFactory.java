package com.john.doe.mini.beans.factory;

import com.john.doe.mini.beans.BeansException;

import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/9.
 */
public interface ListableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
