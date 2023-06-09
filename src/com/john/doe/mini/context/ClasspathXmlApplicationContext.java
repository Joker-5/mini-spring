package com.john.doe.mini.context;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.john.doe.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.john.doe.mini.beans.factory.config.ConfigurableListableBeanFactory;
import com.john.doe.mini.beans.factory.support.DefaultListableBeanFactory;
import com.john.doe.mini.beans.factory.xml.XmlBeanDefinitionReader;
import com.john.doe.mini.core.ClassPathXmlResource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
@Slf4j
public class ClasspathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClasspathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClasspathXmlApplicationContext(String fileName, boolean isRefresh) {
        log.info("load resource from file: {}", fileName);
        beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        reader.loadBeanDefinitions(resource);
        if (isRefresh) {
            try {
                refresh();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void initApplicationEventPublisher() {
        setApplicationEventPublisher(new SimpleApplicationEventPublisher());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public void registerBeanProcessors(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    public void onRefresh() {
        beanFactory.refresh();
    }

    @Override
    public void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context refreshed already."));
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return beanFactory;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        getApplicationEventPublisher().addApplicationListener(listener);
    }
}
