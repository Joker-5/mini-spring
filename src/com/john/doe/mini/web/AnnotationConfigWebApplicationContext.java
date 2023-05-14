package com.john.doe.mini.web;

import com.john.doe.mini.context.ClasspathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
public class AnnotationConfigWebApplicationContext extends ClasspathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
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
