package com.api.utils;

import org.springframework.util.StringUtils;

/**
 * @author coderyong
 */
public class SqlUtils {
    /**
     * 获取分页Sql
     *
     * @param page 页码
     * @param size 每页大小
     * @return Sql字符串
     */
    public static String getPageSql(int page, int size) {
        if (page > 0) {
            size = size == 0 ? 20 : size;
            int startIndex = (page - 1) * size;
            return " LIMIT " + startIndex + "," + size;
        }
        return "";
    }

    /**
     * 获取模糊查询Sql
     *
     * @param keyword    关键字
     * @param jdbcFields 模糊查询字段
     * @return Sql字符串
     */
    public static String getLikeSql(String keyword, String... jdbcFields) {
        StringBuilder builder = new StringBuilder();
        if (jdbcFields != null) {
            for (String field : jdbcFields) {
                if (!StringUtils.isEmpty(builder.toString())) {
                    builder.append(" OR ");
                }
                if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(keyword)) {
                    builder.append(" ").append(field)
                            .append(" LIKE '%").append(keyword).append("%' ");
                }
            }
        }
        return StringUtils.isEmpty(builder.toString()) ? "1=1" : builder.toString();
    }
}
