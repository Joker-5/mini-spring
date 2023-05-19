package com.john.doe.mini.web.servlet;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.util.ArrayUtils;
import com.john.doe.mini.web.RequestMapping;
import com.john.doe.mini.web.WebApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
@Slf4j
public class RequestMappingHandlerMapping implements HandlerMapping {
    private WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    protected void initMapping() {
        Class<?> clazz = null;
        Object obj = null;

        String[] controllerNames = wac.getBeanDefinitionNames();
        log.debug("wac.getBeanDefinitionNames().length: {}", wac.getBeanDefinitionNames().length);
        // get @RequestMapping controllers
        for (String controllerName : controllerNames) {
            log.info("get controller name: {}", controllerName);
            try {
                clazz = Class.forName(controllerName);
                obj = wac.getBean(controllerName);
            } catch (ClassNotFoundException | BeansException e) {
                e.printStackTrace();
            }

            Method[] methods = clazz.getDeclaredMethods();
            if (!ArrayUtils.isEmpty(methods)) {
                for (Method method : methods) {
                    log.info("get controller: {}, method is: {}", controllerName, method.getName());
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        // get url value in annotation
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        mappingRegistry.getUrlMappingNames().add(urlMapping);
                        mappingRegistry.getMappingObjects().put(urlMapping, obj);
                        mappingRegistry.getMappingMethods().put(urlMapping, method);
                    }
                }
            }
        }
    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String url = request.getServletPath();
        log.debug("URL: {}, if contains urlMappingName: {}", url, mappingRegistry.getUrlMappingNames().contains(url));
        
        if (!mappingRegistry.getUrlMappingNames().contains(url)) {
            return null;
        }
        Method method = mappingRegistry.getMappingMethods().get(url);
        Object bean = mappingRegistry.getMappingObjects().get(url);

        return new HandlerMethod(method, bean);
    }
}
