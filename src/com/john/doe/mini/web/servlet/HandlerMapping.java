package com.john.doe.mini.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by JOHN_DOE on 2023/5/17.
 */
public interface HandlerMapping {
    // url to handling method
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
