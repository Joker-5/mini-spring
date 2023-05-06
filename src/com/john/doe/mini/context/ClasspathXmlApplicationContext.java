package com.john.doe.mini.context;

import com.john.doe.mini.beans.*;
import com.john.doe.mini.core.ClassPathXmlResource;
import com.john.doe.mini.core.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class ClasspathXmlApplicationContext implements BeanFactory {
    private BeanFactory beanFactory;

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
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
