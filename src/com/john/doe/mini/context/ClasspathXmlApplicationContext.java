package com.john.doe.mini.context;

import com.john.doe.mini.beans.*;
import com.john.doe.mini.beans.factory.BeanFactory;
import com.john.doe.mini.beans.factory.SimpleBeanFactory;
import com.john.doe.mini.core.ClassPathXmlResource;
import com.john.doe.mini.core.Resource;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class ClasspathXmlApplicationContext implements BeanFactory {
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
    public Boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        beanFactory.registerBean(beanName, obj);
    }


}
