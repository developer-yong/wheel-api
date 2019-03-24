package com.api.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author coderyong
 */
public class Check {

    /**
     * 判断对象数组是否有空值
     *
     * @param objects 对象数组
     * @return true 有空, false 无空
     */
    public static boolean hasEmpty(Object... objects) {
        for (Object s : objects) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Object是否为空
     *
     * @param o Object
     * @return true 空, false 非空
     */
    public static boolean isEmpty(Object o) {
        if (o instanceof CharSequence) {
            CharSequence s = (CharSequence) o;
            if ("nil".equals(s) || "undefined".equals(s)) {
                return true;
            }
            int len = s.length();
            if (len == 0) {
                return true;
            }
            for (int i = 0; i < len; i++) {
                switch (s.charAt(i)) {
                    case ' ':
                    case '\t':
                    case '\n':
                    case '\r':
                    case '\b':
                    case '\f':
                        break;
                    default:
                        return false;
                }
            }
        }
        return o == null || o instanceof Collection && ((Collection) o).isEmpty() || o instanceof Map && ((Map) o).isEmpty();
    }
}
