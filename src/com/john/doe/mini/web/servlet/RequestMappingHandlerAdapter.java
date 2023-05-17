package com.john.doe.mini.web.servlet;

import com.john.doe.mini.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        doHandle(request, response, (HandlerMethod) handler);
    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object bean = handler.getBean();
        Object result = null;

        try {
            result = method.invoke(bean);
            response.getWriter().append(result.toString());
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }
}
