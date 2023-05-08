package com.john.doe.mini.beans.factory.config;

import com.john.doe.mini.beans.BeanScopeEnum;
import com.john.doe.mini.beans.ConstructorArgumentValues;
import com.john.doe.mini.beans.PropertyValues;

import java.util.Arrays;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class BeanDefinition {
    private boolean lazyInit = false;

    private String[] dependsOn;

    private ConstructorArgumentValues constructorArgumentValues;

    private PropertyValues propertyValues;

    private volatile Object beanClass;

    private String id;

    private String className;

    private String scope = BeanScopeEnum.SINGLETON.getName();

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return scope.equals(BeanScopeEnum.SINGLETON.getName());
    }

    public boolean isPrototype() {
        return scope.equals(BeanScopeEnum.PROTOTYPE.getName());
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Object beanClass) {
        this.beanClass = beanClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "lazyInit=" + lazyInit +
                ", dependsOn=" + Arrays.toString(dependsOn) +
                ", constructorArgumentValues=" + constructorArgumentValues +
                ", propertyValues=" + propertyValues +
                ", beanClass=" + beanClass +
                ", id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
