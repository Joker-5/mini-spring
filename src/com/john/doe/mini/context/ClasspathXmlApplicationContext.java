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
    private SimpleBeanFactory beanFactory;

    public ClasspathXmlApplicationContext(String fileName) {
        // load resource from xml file
        Resource resource = new ClassPathXmlResource(fileName);
        this.beanFactory = new SimpleBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // load bean definitions from resource
        reader.loadBeanDefinitions(resource);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return beanFactory.getType(name);
    }


    @Override
    public void publishEvent(ApplicationEvent event) {
        // TODO
    }
}
