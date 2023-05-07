package com.john.doe.mini.beans.factory;

import com.john.doe.mini.beans.ConstructorArgumentValue;
import com.john.doe.mini.beans.ConstructorArgumentValues;
import com.john.doe.mini.beans.PropertyValue;
import com.john.doe.mini.beans.PropertyValues;
import com.john.doe.mini.beans.factory.config.BeanDefinition;
import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.config.BeanDefinitionRegistry;

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
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private final List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        beanDefinitionMap.put(bd.getId(), bd);
        beanDefinitionNames.add(beanName);
        if (!bd.isLazyInit()) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
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
            System.out.println(String.format("get bean: [%s] null", beanName));
            BeanDefinition bd = beanDefinitionMap.get(beanName);
            if (bd == null) {
                throw new BeansException(String.format("Bean:[%s] not found", beanName));
            }
            singleton = createBean(bd);
            registerSingleton(beanName, singleton);
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

    private Object createBean(BeanDefinition bd) {
        Class<?> clazz = null;
        Constructor<?> ctor = null;
        Object obj = null;

        try {
            clazz = Class.forName(bd.getClassName());
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
//                System.out.println("argumentValue: " + argumentValue);
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
                try {
                    // instantiate bean
                    obj = ctor.newInstance(paramValues);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }

        // resolve setter args
        PropertyValues pvs = bd.getPropertyValues();
        if (!pvs.isEmpty()) {
            for (PropertyValue pv : pvs.getPropertyValues()) {
                String pName = pv.getName();
                String pType = pv.getType();

                Class<?> paramType = null;
                Object paramValue = pv.getValue();

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

        return obj;
    }
}
