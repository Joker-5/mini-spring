package com.john.doe.mini.context;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.XmlBeanDefinitionReader;
import com.john.doe.mini.beans.factory.BeanFactory;
import com.john.doe.mini.beans.factory.SimpleBeanFactory;
import com.john.doe.mini.core.ClassPathXmlResource;
import com.john.doe.mini.core.Resource;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class ClasspathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private SimpleBeanFactory bf;

    public ClasspathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClasspathXmlApplicationContext(String fileName, boolean isRefresh) {
        // load resource from xml file
        Resource resource = new ClassPathXmlResource(fileName);
        this.bf = new SimpleBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        // load bean definitions from resource
        reader.loadBeanDefinitions(resource);
        if (isRefresh) {
            bf.refresh();
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.bf.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return bf.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return bf.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return bf.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return bf.getType(name);
    }


    @Override
    public void publishEvent(ApplicationEvent event) {
        // TODO
    }
}
