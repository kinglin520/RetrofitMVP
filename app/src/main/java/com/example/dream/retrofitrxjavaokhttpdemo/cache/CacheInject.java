package com.example.dream.retrofitrxjavaokhttpdemo.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenlin
 * 仅支持path为final的请求接口服务提供缓存
 * 注入注释，处理工具
 */
public class CacheInject {

    public static Map<String, Object> map = new HashMap<>(32);

    public static void inject(Class<?> clazz) {
        Method[] m = clazz.getDeclaredMethods();
        for (Method ms : m) {
            if (ms.isAnnotationPresent(Cache.class)) {
                Cache cache = ms.getAnnotation(Cache.class);
                if (cache != null) {
                    String path = cache.path();
                    boolean mode = cache.mode();
                    if (!map.containsKey(path)) {
                        //添加需要拦截的接口
                        map.put(path, mode);
                    }
                }
            }
        }
    }
}
