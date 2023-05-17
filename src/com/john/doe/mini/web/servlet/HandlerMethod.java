package com.john.doe.mini.web.servlet;

import java.lang.reflect.Method;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
public class HandlerMethod {
    private Object bean;

    private Class<?> beanType;

    private Method method;

    private MethodParameter[] parameters;

    private Class<?> returnType;

    private String description;

    private String className;

    private String methodName;

    public HandlerMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public void setParameters(MethodParameter[] parameters) {
        this.parameters = parameters;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
