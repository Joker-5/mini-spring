package com.john.doe.mini.context;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.john.doe.mini.beans.factory.config.AutowiredCapableBeanFactory;
import com.john.doe.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.john.doe.mini.beans.factory.xml.XmlBeanDefinitionReader;
import com.john.doe.mini.beans.factory.BeanFactory;
import com.john.doe.mini.core.ClassPathXmlResource;
import com.john.doe.mini.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class ClasspathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private final AutowiredCapableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClasspathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClasspathXmlApplicationContext(String fileName, boolean isRefresh) {
        // load resource from xml file
        Resource resource = new ClassPathXmlResource(fileName);
        this.beanFactory = new AutowiredCapableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // load bean definitions from resource
        reader.loadBeanDefinitions(resource);
        if (isRefresh) {
            refresh();
        }
    }

    public void refresh() {
        registerBeanPostProcessors(beanFactory);
        onRefresh();
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    private void registerBeanPostProcessors(AutowiredCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh() {
        beanFactory.refresh();
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
