package com.github.xindeqiang0125.fastcode.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceUtils {

    private static final Map<String, String> resourceTextCache = new ConcurrentHashMap<>();

    public static String loadClasspathResourceText(String path) {
        String result = resourceTextCache.get(path);
        if (result != null) {
            return result;
        }
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(path);
        if (stream == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        result = reader.lines().reduce((s, s2) -> StringUtils.joinWith("\n", s, s2)).orElse(null);
        resourceTextCache.put(path, result);
        return result;
    }
}
