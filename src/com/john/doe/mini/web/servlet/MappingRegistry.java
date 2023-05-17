package com.john.doe.mini.web.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
public class MappingRegistry {
    // URL from @RequestMapping annotation
    private List<String> urlMappingNames = new ArrayList<>();

    // URL to bean obj
    private Map<String, Object> mappingObjects = new HashMap<>();

    // URL to method to invoke
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjects() {
        return mappingObjects;
    }

    public void setMappingObjects(Map<String, Object> mappingObjects) {
        this.mappingObjects = mappingObjects;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }
}
