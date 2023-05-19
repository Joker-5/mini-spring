package com.john.doe.mini.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public final class WebUtils {
    private WebUtils() {
        throw new UnsupportedOperationException("Private constructor can not be accessed");
    }

    public static Map<String, Object> getParametersStartsWith(HttpServletRequest request, String prefix) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();

        if (prefix == null) {
            prefix = "";
        }

        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (prefix.isEmpty() || paramName.startsWith(prefix)) {
                String rmPrefix = paramName.substring(prefix.length());
                params.put(rmPrefix, request.getParameter(paramName));
            }
        }

        return params;
    }
}
