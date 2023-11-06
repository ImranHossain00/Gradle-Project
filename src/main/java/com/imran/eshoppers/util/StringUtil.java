package eshoppers.util;

public class StringUtil {
    public static boolean isNotEmpty(String action) {
        return !isEmpty(action);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }
}
