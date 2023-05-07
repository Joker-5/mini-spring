package com.john.doe.mini.beans;

import com.john.doe.mini.beans.factory.BeanFactory;
import com.john.doe.mini.beans.factory.SimpleBeanFactory;
import com.john.doe.mini.core.Resource;
import org.dom4j.Element;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class XmlBeanDefinitionReader {
    private SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();

            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
