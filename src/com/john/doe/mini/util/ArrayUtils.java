package com.john.doe.mini.util;

/**
 * Created by JOHN_DOE on 2023/5/9.
 */
public class ArrayUtils {
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
