package com.api.utils;

import java.util.Random;
import java.util.UUID;


/**
 * UUID工具类
 *
 * @author coderyong
 */
public class UUIDUtils {
    /**
     * 获取去掉 "-" 的UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取去掉 "-" 的UUID
     *
     * @return UUID
     */
    public static int uuidInt() {
        return Integer.parseInt(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    /**
     * 获取去掉 "-" 的UUID
     *
     * @return UUID
     */
    public static String createOrderId() {
        return "DBGW" + uuid().substring(0, 16);
    }

    /**
     * 生成随机长度数字
     *
     * @param length 长度
     * @return 字符串
     */
    public static String getNumberId(int length) {
        return getRandomString(length, "0123456789");
    }

    /**
     * 生成随机长度字母数字混合字符串
     *
     * @param length 长度
     * @return 字符串
     */
    public static String getId(int length) {
        return getRandomString(length, "abcdefghijklmnopqrstuvwxyz0123456789");
    }


    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @param base   字符串范围
     * @return 返回字符串
     */
    private static String getRandomString(int length, String base) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
