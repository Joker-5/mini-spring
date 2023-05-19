package com.john.doe.mini.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public class WebDataBinderFactory {
    public WebDataBinder createBinder(HttpServletRequest request, Object target, String targetName) {
        WebDataBinder webDataBinder = new WebDataBinder(target, targetName);
        initBinder(webDataBinder, request);

        return webDataBinder;
    }

    protected void initBinder(WebDataBinder webDataBinder, HttpServletRequest request) {

    }
}
