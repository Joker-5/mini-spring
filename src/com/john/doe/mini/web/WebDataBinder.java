package com.john.doe.mini.web;

import com.john.doe.mini.beans.PropertyEditor;
import com.john.doe.mini.beans.factory.PropertyValues;
import com.john.doe.mini.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public class WebDataBinder {
    private Object target;

    private Class<?> clazz;

    private String targetName;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.clazz = target.getClass();
        this.targetName = targetName;
    }

    // bind request param to the property of target object(bean)
    public void bind(HttpServletRequest request) {
        PropertyValues pvs = assignParameters(request);
        addBindValues(pvs, request);
        doBind(pvs);
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

    private void doBind(PropertyValues pvs) {
        applyPropertyValues(pvs);
    }

    protected void applyPropertyValues(PropertyValues pvs) {
        getPropertyAccessor().setPropertyValues(pvs);
    }

    protected BeanWrapperImpl getPropertyAccessor() {
        return new BeanWrapperImpl(target);
    }

    protected void addBindValues(PropertyValues pvs, HttpServletRequest request) {

    }

    // resolve request param to pvs
    private PropertyValues assignParameters(HttpServletRequest request) {
        // K: name, V: value
        Map<String, Object> map = WebUtils.getParametersStartsWith(request, "");

        return new PropertyValues(map);
    }
}
