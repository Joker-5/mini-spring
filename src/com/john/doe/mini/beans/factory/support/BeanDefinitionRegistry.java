package com.john.doe.mini.beans.factory.support;

import com.john.doe.mini.beans.factory.config.BeanDefinition;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition bd);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
