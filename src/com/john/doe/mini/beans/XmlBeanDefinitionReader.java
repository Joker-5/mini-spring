package com.john.doe.mini.beans;

import com.john.doe.mini.core.Resource;
import org.dom4j.Element;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class XmlBeanDefinitionReader {
    private BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();

            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
