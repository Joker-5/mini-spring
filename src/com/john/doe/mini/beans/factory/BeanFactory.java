package com.john.doe.mini.beans.factory;

import com.john.doe.mini.beans.BeanDefinition;
import com.john.doe.mini.beans.BeansException;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    
    Boolean containsBean(String name);
    
    void registerBean(String beanName,Object obj);
}
