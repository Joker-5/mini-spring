package com.john.doe.mini.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
public class ContextLoaderListener implements ServletContextListener {
    private WebApplicationContext context;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initWebApplicationContext(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void initWebApplicationContext(ServletContext servletContext) {
        String contextConfigLocation = servletContext.getInitParameter(WebConstant.CONTEXT_CONFIG_LOCATION);
        // start IoC container when servlet server start
        WebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext(contextConfigLocation);
        webApplicationContext.setServletContext(servletContext);
        this.context = webApplicationContext;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
    }
}
