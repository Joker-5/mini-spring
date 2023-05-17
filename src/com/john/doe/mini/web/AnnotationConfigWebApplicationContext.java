package com.john.doe.mini.web;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.john.doe.mini.beans.factory.config.BeanDefinition;
import com.john.doe.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.john.doe.mini.beans.factory.config.ConfigurableListableBeanFactory;
import com.john.doe.mini.beans.factory.support.DefaultListableBeanFactory;
import com.john.doe.mini.context.AbstractApplicationContext;
import com.john.doe.mini.context.ApplicationEvent;
import com.john.doe.mini.context.ApplicationListener;
import com.john.doe.mini.context.SimpleApplicationEventPublisher;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
@Slf4j
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    private WebApplicationContext parentApplicationContext;

    private DefaultListableBeanFactory beanFactory;

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessorList = new ArrayList<>();

    public AnnotationConfigWebApplicationContext(String fileName) {
        this(fileName, null);
    }

    public AnnotationConfigWebApplicationContext(String fileName, WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        servletContext = parentApplicationContext.getServletContext();

        URL xmlPath = null;
        try {
            xmlPath = getServletContext().getResource(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = XmlScanComponentHelper.scanPackages(packageNames, getClass());

        beanFactory = new DefaultListableBeanFactory();
        beanFactory.setParentBeanFactory(parentApplicationContext.getBeanFactory());
        loadBeanDefinitions(controllerNames);

        try {
            refresh();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    public WebApplicationContext getParentApplicationContext() {
        return parentApplicationContext;
    }

    public void setParentApplicationContext(WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        beanFactory.setParentBeanFactory(parentApplicationContext.getBeanFactory());
    }

    public void setBeanFactory(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessorList() {
        return beanFactoryPostProcessorList;
    }

    public void setBeanFactoryPostProcessorList(List<BeanFactoryPostProcessor> beanFactoryPostProcessorList) {
        this.beanFactoryPostProcessorList = beanFactoryPostProcessorList;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void registerListeners() {
        getApplicationEventPublisher().addApplicationListener(new ApplicationListener());
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

    private void loadBeanDefinitions(List<String> controllerNames) {
        for (String controllerName : controllerNames) {
            BeanDefinition beanDefinition = new BeanDefinition(controllerName, controllerName);
            beanFactory.registerBeanDefinition(controllerName, beanDefinition);
        }
    }
}
