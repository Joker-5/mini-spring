package com.john.doe.mini.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
@Slf4j
public class ContextLoaderListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext wac;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initWebApplicationContext(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void initWebApplicationContext(ServletContext servletContext) {
        String contextConfigLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);

        // start IoC container when servlet server start
        WebApplicationContext wac = new XmlWebApplicationContext(contextConfigLocation);
        log.info("initWebApplicationContext by ContextLoaderListener");
        wac.setServletContext(servletContext);
        this.wac = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
    }
}
