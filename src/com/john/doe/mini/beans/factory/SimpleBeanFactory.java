package com.john.doe.mini.beans.factory;

import com.john.doe.mini.beans.ConstructorArgumentValue;
import com.john.doe.mini.beans.ConstructorArgumentValues;
import com.john.doe.mini.beans.PropertyValue;
import com.john.doe.mini.beans.PropertyValues;
import com.john.doe.mini.beans.factory.config.BeanDefinition;
import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.config.BeanDefinitionRegistry;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by JOHN_DOE on 2023/5/6.
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    private final List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        beanDefinitionMap.put(beanName, bd);
        beanDefinitionNames.add(beanName);
    }

    @Override
    public void removeBeanDefinition(String name) {
        beanDefinitionMap.remove(name);
        beanDefinitionNames.remove(name);
        removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return beanDefinitionMap.containsKey(name);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = getSingleton(beanName);

        if (singleton == null) {
            // get singleton from cache
            singleton = earlySingletonObjects.get(beanName);
            if (singleton == null) {
                BeanDefinition bd = beanDefinitionMap.get(beanName);
                // bean not exist, try to create it
                singleton = createBean(bd);
                log.info("before register singleton, beanName: {} singleton: {}", beanName, singleton);
                registerSingleton(beanName, singleton);
                // TODO postprocessor
            }
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return beanDefinitionMap.get(name).isSingleton();

    }

    @Override
    public boolean isPrototype(String name) {
        return beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return beanDefinitionMap.get(name).getClass();
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    private Object createBean(BeanDefinition bd) {
        Class<?> clazz = null;
        Object obj = doCreateBean(bd);
        // after bean created, put it into cache
        earlySingletonObjects.put(bd.getId(), obj);

        try {
            clazz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // handle properties for bean
        handleProperties(bd, clazz, obj);

        return obj;
    }

    /**
     * create early bean
     *
     * @param bd
     * @return
     */
    private Object doCreateBean(BeanDefinition bd) {
        Class<?> clazz = null;
        Constructor<?> ctor = null;
        Object obj = null;

        try {
            clazz = Class.forName(bd.getClassName());
            log.info("doCreateBean get bean class: {}", clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // resolve constructor args
        ConstructorArgumentValues avs = bd.getConstructorArgumentValues();
        if (!avs.isEmpty()) {
            Class<?>[] paramTypes = new Class<?>[avs.getConstructorArgumentCount()];
            Object[] paramValues = new Object[avs.getConstructorArgumentCount()];

            for (int i = 0; i < avs.getConstructorArgumentCount(); i++) {
                ConstructorArgumentValue argumentValue = avs.getIndexedConstructorArgumentValue(i);
                switch (argumentValue.getType()) {
                    case "String":
                    case "java.lang.String": {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                        break;
                    }
                    case "Integer":
                    case "java.lang.Integer": {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.parseInt((String) argumentValue.getValue());
                        break;
                    }
                    case "int": {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.parseInt((String) argumentValue.getValue());
                        break;
                    }
                    default: { // default String
                        paramTypes[i] = String.class;
                        paramValues[i] = (String) argumentValue.getValue();
                        break;
                    }
                }
            }
            try {
                ctor = clazz.getConstructor(paramTypes);
                // instantiate bean
                obj = ctor.newInstance(paramValues);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        log.info("Bean: {} created, className: {}, obj: {}", bd.getId(), bd.getBeanClass(), obj);
        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clazz, Object obj) {
        // resolve setter args
        PropertyValues pvs = bd.getPropertyValues();

        if (!pvs.isEmpty()) {
            for (PropertyValue pv : pvs.getPropertyValueList()) {
                String pName = pv.getName();
                String pType = pv.getType();
                Object pValue = pv.getValue();
                boolean isRef = pv.getIsRef();

                Class<?> paramType = null;
                Object paramValue = null;

                if (!isRef) { // normal property
                    switch (pType) {
                        case "String":
                        case "java.lang.String": {
                            paramType = String.class;
                            break;
                        }
                        case "Integer":
                        case "java.lang.Integer": {
                            paramType = Integer.class;
                            break;
                        }
                        case "int": {
                            paramType = int.class;
                            break;
                        }
                        default: { // default String
                            paramType = String.class;
                            break;
                        }
                    }
                    paramValue = pValue;
                } else { // ref bean property
                    try {
                        paramType = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        // try to create dependent bean instance
                        paramValue = getBean((String) pValue);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }


                // find a method to invoke
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;

                try {
                    method = clazz.getMethod(methodName, paramType);
                    method.invoke(obj, paramValue);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
