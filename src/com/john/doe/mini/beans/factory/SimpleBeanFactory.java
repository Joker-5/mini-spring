package com.john.doe.mini.beans.factory;

import com.john.doe.mini.beans.BeanDefinition;
import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.BeanFactory;

import java.util.*;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = getSingleton(beanName);

        if (Objects.isNull(singleton)) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (Objects.isNull(beanDefinition)) {
                throw new BeansException(String.format("Bean: %s not found", beanName));
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            registerSingleton(beanName, singleton);
        }
        return singleton;
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        registerSingleton(beanName, obj);
    }
}
