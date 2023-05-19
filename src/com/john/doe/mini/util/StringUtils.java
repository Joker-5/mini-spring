package com.john.doe.mini.util;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("Private constructor can not be accessed");
    }

    public static boolean hasLength(String s) {
        return s != null && !s.isEmpty();
    }

    public static boolean hasText(String s) {
        return s != null && !s.isEmpty() && containsText(s);
    }

    public static String trimAllWhitespace(String s) {
        if (!hasLength(s)) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                sb.append(s.charAt(i));
            }
        }

        return sb.toString();
    }

    private static boolean containsText(CharSequence s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
