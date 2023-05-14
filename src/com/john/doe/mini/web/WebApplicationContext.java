package com.john.doe.mini.web;

import com.john.doe.mini.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);
}
