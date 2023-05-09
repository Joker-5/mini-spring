package com.john.doe.mini.beans.factory.config;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.BeanFactory;

/**
 * Created by JOHN_DOE on 2023/5/9.
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
