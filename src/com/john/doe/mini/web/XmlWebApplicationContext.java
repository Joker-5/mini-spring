package com.john.doe.mini.web;

import com.john.doe.mini.context.ClasspathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
public class XmlWebApplicationContext extends ClasspathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
