package com.john.doe.mini.beans;

import com.john.doe.mini.beans.factory.SimpleBeanFactory;
import com.john.doe.mini.beans.factory.config.BeanDefinition;
import com.john.doe.mini.core.Resource;
import com.john.doe.mini.core.XmlResourceConstant;
import org.dom4j.Element;

import java.util.List;

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

            String beanId = element.attributeValue(XmlResourceConstant.ID);
            String beanClassName = element.attributeValue(XmlResourceConstant.CLASS);
            BeanDefinition bd = new BeanDefinition(beanId, beanClassName);

            // resolve property tag
            List<Element> propertyElements = element.elements(XmlResourceConstant.PROPERTY);
            PropertyValues pvs = new PropertyValues();
            for (Element propertyElement : propertyElements) {
                String type = propertyElement.attributeValue(XmlResourceConstant.TYPE);
                String name = propertyElement.attributeValue(XmlResourceConstant.NAME);
                String value = propertyElement.attributeValue(XmlResourceConstant.VALUE);

                pvs.addPropertyValue(new PropertyValue(type, name, value));
            }
            bd.setPropertyValues(pvs);

            // resolve constructor-arg tag
            List<Element> constructorElements = element.elements(XmlResourceConstant.CONSTRUCTOR_ARG);
            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            for (Element constructorElement : constructorElements) {
                String type = constructorElement.attributeValue(XmlResourceConstant.TYPE);
                String name = constructorElement.attributeValue(XmlResourceConstant.NAME);
                String value = constructorElement.attributeValue(XmlResourceConstant.VALUE);

                avs.addConstructorArgumentValue(new ConstructorArgumentValue(type, name, value));
                System.out.println(String.format("resolve constructor args, type: [%s], name: [%s], value: [%s]", type, name, value));
            }
            bd.setConstructorArgumentValues(avs);

            simpleBeanFactory.registerBeanDefinition(beanId, bd);
        }
    }
}
