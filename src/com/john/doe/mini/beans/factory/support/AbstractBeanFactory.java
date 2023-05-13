package com.john.doe.mini.beans.factory.support;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.PropertyValue;
import com.john.doe.mini.beans.factory.PropertyValues;
import com.john.doe.mini.beans.factory.config.BeanDefinition;
import com.john.doe.mini.beans.factory.config.ConfigurableBeanFactory;
import com.john.doe.mini.beans.factory.config.ConstructorArgumentValue;
import com.john.doe.mini.beans.factory.config.ConstructorArgumentValues;
import com.john.doe.mini.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
@Slf4j
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {
    protected final List<String> beanDefinitionNames = new ArrayList<>();

    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    public AbstractBeanFactory() {
    }

    abstract public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeansException;

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
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
                registerSingleton(beanName, singleton);
                // postProcessBeforeInitialization
                applyBeanPostProcessorsBeforeInitialization(singleton, beanName);
                // init-method
                if (!StringUtils.isEmpty(bd.getInitMethodName())) {
                    invokeInitMethod(bd, singleton);
                }
                // postProcessAfterInitialization
                applyBeanPostProcessorsAfterInitialization(bd, beanName);
            }
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        beanDefinitionMap.put(beanName, bd);
        beanDefinitionNames.add(beanName);
        log.info("register beanDefinition, beanName: [{}], bd:\n {}", beanName, bd);
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

    public void registerBean(String beanName, Object obj) {
        registerSingleton(beanName, obj);
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

        // populate properties for bean
        populateBean(bd, clazz, obj);
        return obj;
    }

    private void invokeInitMethod(BeanDefinition bd, Object obj) {
        Class<?> clazz = bd.getClass();
        Method method = null;

        try {
            method = clazz.getMethod(bd.getInitMethodName());
            method.invoke(obj);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
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
        log.info("Bean: [{}] created, className: [{}], obj: [{}]", bd.getId(), bd.getClassName(), obj);
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
                        // try to create dependent bean instance
                        paramValue = getBean((String) pValue);
                    } catch (ClassNotFoundException | BeansException e) {
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

    private void populateBean(BeanDefinition bd, Class<?> clazz, Object obj) {
        handleProperties(bd, clazz, obj);
        log.info("Bean: [{}] has populated properties.", bd.getId());
    }
}
