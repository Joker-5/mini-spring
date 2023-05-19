package com.john.doe.mini.util;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public final class BeanUtils {
    private static final String SET = "set";

    public static final String GET = "get";

    private BeanUtils() {
        throw new UnsupportedOperationException("Private constructor can not be accessed");
    }

    public static String getSetterName(String fieldName) {
        if (!StringUtils.hasLength(fieldName)) {
            return fieldName;
        }
        return SET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String getGetterName(String fieldName) {
        if (!StringUtils.hasLength(fieldName)) {
            return fieldName;
        }
        return GET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
