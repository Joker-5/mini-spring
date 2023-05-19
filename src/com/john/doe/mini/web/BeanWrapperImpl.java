package com.john.doe.mini.web;

import com.john.doe.mini.beans.PropertyEditor;
import com.john.doe.mini.beans.PropertyEditorRegistrySupport;
import com.john.doe.mini.beans.factory.PropertyValue;
import com.john.doe.mini.beans.factory.PropertyValues;
import com.john.doe.mini.util.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    // target wrapped object
    private Object beanInstance;

    private Class<?> clazz;

    private PropertyValues propertyValues;

    public BeanWrapperImpl(Object beanInstance) {
        registerDefaultEditors();

        this.beanInstance = beanInstance;
        this.clazz = beanInstance.getClass();
    }

    public void setBeanInstance(Object beanInstance) {
        this.beanInstance = beanInstance;
    }

    public Object getBeanInstance() {
        return beanInstance;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
        for (PropertyValue pv : propertyValues.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    // bind pv to bean property
    public void setPropertyValue(PropertyValue pv) {
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        // the priority of CustomEditor is higher than DefaultEditor
        PropertyEditor pe = getCustomEditor(propertyHandler.getPropertyClass());
        if (pe == null) {
            pe = getDefaultEditor(propertyHandler.getPropertyClass());
        }
        
        pe.setAsText((String) pv.getValue());
        // set property value to wrapped bean instance
        propertyHandler.setValue(pe.getValue());
    }

    // an inner class to handler property by setter and getter method
    class BeanPropertyHandler {
        private Method setter;

        private Method getter;

        Class<?> propertyClass;

        public BeanPropertyHandler(String propertyName) {
            try {
                Field field = clazz.getDeclaredField(propertyName);

                propertyClass = field.getType();
                setter = clazz.getDeclaredMethod(BeanUtils.getSetterName(propertyName));
                getter = clazz.getDeclaredMethod(BeanUtils.getGetterName(propertyName));
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public Object getValue() {
            Object result = null;
            setter.setAccessible(true);
            try {
                result = getter.invoke(beanInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            return result;
        }

        public void setValue(Object value) {
            setter.setAccessible(true);
            try {
                setter.invoke(beanInstance, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        public Class<?> getPropertyClass() {
            return propertyClass;
        }
    }
}
