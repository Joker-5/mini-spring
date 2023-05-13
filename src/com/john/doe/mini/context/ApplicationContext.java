package com.john.doe.mini.context;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.ListableBeanFactory;
import com.john.doe.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.john.doe.mini.beans.factory.config.ConfigurableBeanFactory;
import com.john.doe.mini.beans.factory.config.ConfigurableListableBeanFactory;
import com.john.doe.mini.core.env.Environment;
import com.john.doe.mini.core.env.EnvironmentCapable;

/**
 * Created by JOHN_DOE on 2023/5/13.
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
