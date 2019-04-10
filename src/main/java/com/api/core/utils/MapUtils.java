package com.api.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Map工具类
 */
public class MapUtils {

    /**
     * Object转Map集合
     *
     * @param obj Object
     * @return Map
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj != null) {
            //创建字段集合
            List<Field> fields = new ArrayList<>();
            //获取Class对象
            Class<?> clazz = obj.getClass();
            //循环获取所有字段，包括父类字段
            while (clazz != null) {
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }
            //创建Map对象
            Map<String, Object> map = new HashMap<>();
            //将所有字段添加到Map中
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }
        return null;
    }

    /**
     * Map集合转Object
     *
     * @param map   Map集合
     * @param clazz Object.class
     * @param <T>   Object泛型类
     * @return Object
     * @throws Exception Exception
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            //创建字段集合
            List<Field> fields = new ArrayList<>();
            //获取Class对象
            Class<?> c = clazz;
            //循环获取所有字段，包括父类字段
            while (c != null) {
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                c = c.getSuperclass();
            }
            //设置所有非static、final修饰字段的值
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    field.set(obj, map.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }
}
