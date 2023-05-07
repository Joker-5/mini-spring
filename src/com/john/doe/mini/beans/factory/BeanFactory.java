package com.john.doe.mini.beans.factory;

import com.john.doe.mini.beans.BeansException;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}
