package com.john.doe.mini.beans.factory.config;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.BeanFactory;


/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;

    int AUTOWIRE_BY_NAME = 1;

    int AUTOWIRE_BY_TYPE = 2;
    
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;
}
