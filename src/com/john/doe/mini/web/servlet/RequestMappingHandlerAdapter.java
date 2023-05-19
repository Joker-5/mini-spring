package com.john.doe.mini.web.servlet;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.web.WebApplicationContext;
import com.john.doe.mini.web.WebBindingInitializer;
import com.john.doe.mini.web.WebDataBinder;
import com.john.doe.mini.web.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private WebApplicationContext wac;

    private WebBindingInitializer webBindingInitializer;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            webBindingInitializer = (WebBindingInitializer) wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        doHandle(request, response, (HandlerMethod) handler);
    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        try {
            invokeHandlerMethod(request, response, handler);
        } catch (IllegalAccessException | InvocationTargetException | IOException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Method invocableMethod = handlerMethod.getMethod();

        Parameter[] parameters = invocableMethod.getParameters();
        Object[] methodParamObjects = new Object[parameters.length];
        int i = 0;

        for (Parameter parameter : parameters) {
            Object methodParamObj = parameter.getType().newInstance();
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, parameter.getName());
            wdb.bind(request);
            methodParamObjects[i++] = methodParamObj;
        }

        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjects);
        response.getWriter().append(returnObj.toString());
    }
}
