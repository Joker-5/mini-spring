package com.john.doe.mini.beans;

import java.util.*;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class SimpleBeanFactory implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    private Map<String, Object> singletons = new HashMap<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = singletons.get(beanName);
        // bean not found, try to instantiate it
        if (Objects.isNull(singleton)) {
            if (!beanDefinitions.containsKey(beanName)) {
                throw new BeansException(String.format("Bean: %s not found in IoC container", beanName));
            }
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            try {
                Class<?> clazz = Class.forName(beanDefinition.getClassName());
                singleton = clazz.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            singletons.put(beanName, singleton);
        }

        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }
}
