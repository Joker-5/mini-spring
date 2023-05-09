package com.john.doe.mini.beans.factory.xml;

import com.john.doe.mini.beans.factory.config.ConstructorArgumentValue;
import com.john.doe.mini.beans.factory.config.ConstructorArgumentValues;
import com.john.doe.mini.beans.factory.PropertyValue;
import com.john.doe.mini.beans.factory.PropertyValues;
import com.john.doe.mini.beans.factory.support.AbstractBeanFactory;
import com.john.doe.mini.beans.factory.config.BeanDefinition;
import com.john.doe.mini.core.Resource;
import com.john.doe.mini.core.XmlResourceConstant;
import com.john.doe.mini.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
@Slf4j
public class XmlBeanDefinitionReader {
    private AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * load bean definitions from xml
     *
     * @param resource
     */
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();

            String beanId = element.attributeValue(XmlResourceConstant.ID);
            String beanClassName = element.attributeValue(XmlResourceConstant.CLASS);
            BeanDefinition bd = new BeanDefinition(beanId, beanClassName);

            // resolve constructor-arg tag
            List<Element> constructorElements = element.elements(XmlResourceConstant.CONSTRUCTOR_ARG);
            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            for (Element constructorElement : constructorElements) {
                String type = constructorElement.attributeValue(XmlResourceConstant.TYPE);
                String name = constructorElement.attributeValue(XmlResourceConstant.NAME);
                String value = constructorElement.attributeValue(XmlResourceConstant.VALUE);

                avs.addConstructorArgumentValue(new ConstructorArgumentValue(type, name, value));
            }
            bd.setConstructorArgumentValues(avs);

            // resolve property tag
            List<Element> propertyElements = element.elements(XmlResourceConstant.PROPERTY);
            PropertyValues pvs = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element propertyElement : propertyElements) {
                String type = propertyElement.attributeValue(XmlResourceConstant.TYPE);
                String name = propertyElement.attributeValue(XmlResourceConstant.NAME);
                String value = propertyElement.attributeValue(XmlResourceConstant.VALUE);
                String ref = propertyElement.attributeValue(XmlResourceConstant.REF);

                boolean isRef = false;
                String pv = "";
                if (!StringUtils.isEmpty(value)) {// bean value in value attr
                    pv = value;
                } else if (!StringUtils.isEmpty(ref)) {// bean value in ref attr
                    isRef = true;
                    pv = ref;
                    refs.add(ref);
                }
                pvs.addPropertyValue(new PropertyValue(type, name, pv, isRef));
            }
            bd.setPropertyValues(pvs);
            bd.setDependsOn(refs.toArray(new String[0]));
            
            beanFactory.registerBeanDefinition(beanId, bd);
        }
    }
}
