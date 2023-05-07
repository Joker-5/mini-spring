package com.john.doe.mini.beans.factory.config;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName,Object singletonObject);
    
    Object getSingleton(String beanName);
    
    boolean containsSingleton(String beanName);
    
    String[] getSingletonNames();
    
    void removeSingleton(String beanName);
}
