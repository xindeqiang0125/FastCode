package com.github.xindeqiang0125.fastcode.utils;

public class StringUtils {

    /**
     * 驼峰转为下划线
     */
    public static String camelToUnderCase(String input) {
        if (input == null) {
            return input;
        } else {
            int length = input.length();
            StringBuilder result = new StringBuilder(length * 2);
            int resultLength = 0;
            boolean wasPrevTranslated = false;

            for (int i = 0; i < length; ++i) {
                char c = input.charAt(i);
                if (i > 0 || c != '_') {
                    if (Character.isUpperCase(c)) {
                        if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
                            result.append('_');
                            ++resultLength;
                        }

                        c = Character.toLowerCase(c);
                        wasPrevTranslated = true;
                    } else {
                        wasPrevTranslated = false;
                    }

                    result.append(c);
                    ++resultLength;
                }
            }
            return resultLength > 0 ? result.toString() : input;
        }
    }

    /**
     * 下划线转驼峰
     */
    public static String underCaseToCamel(String input) {
        if (input == null) {
            return null;
        }
        input = input.toLowerCase();
        char last = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '_') {
                sb.append(last == '_' ? Character.toUpperCase(c) : c);
            }
            last = c;
        }
        return sb.toString();
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().equals("");
    }

    public static String toUpper(String field, int index) {
        if (field == null) return null;
        char[] chars = field.toCharArray();
        chars[index] = Character.toUpperCase(chars[index]);
        return new String(chars);
    }
}
