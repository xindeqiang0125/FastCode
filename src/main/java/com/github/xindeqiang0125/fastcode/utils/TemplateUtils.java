package com.github.xindeqiang0125.fastcode.utils;

public class TemplateUtils {

    private static final String TEMPLATE_PATH_PATTERN = "template/%s.vm";

    public static String getTemplateText(Class<?> clz) {
        if (clz == null) return null;
        return ResourceUtils.loadClasspathResourceText(String.format(TEMPLATE_PATH_PATTERN, clz.getSimpleName()));
    }
}
