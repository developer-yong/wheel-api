package com.api.core;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author coderyong
 */
public interface IProvider {

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    String tableName();

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    default String createSelectSql() {
        return tableName() + ".*";
    }

    /**
     * 创建FormSql
     *
     * @param selectModel 查询需要的字段
     * @return Sql字符串
     */
    default String createSelectSql(Map<String, Object> selectModel) {
        return createSelectSql();
    }

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    default String createFromSql() {
        return tableName();
    }

    /**
     * 创建FormSql
     *
     * @param selectModel 查询需要的字段
     * @return Sql字符串
     */
    default String createFromSql(Map<String, Object> selectModel) {
        return createFromSql();
    }

    /**
     * 创建查询条件Sql
     *
     * @param selectModel 查询需要的字段
     * @return 查询条件Sql
     */
    default String createWhereSql(Map<String, Object> selectModel) {
        return "";
    }

    /**
     * 附加其他Sql，比如ORDER BY、GROUP BY 等
     *
     * @return 查询条件Sql
     */
    default void appendOtherSql(SQL sql) {
    }

    /**
     * 附加其他Sql，比如ORDER BY、GROUP BY 等
     *
     * @param selectModel 查询需要的字段
     * @return 附加Sql
     */
    default void appendOtherSql(SQL sql, Map<String, Object> selectModel) {
        appendOtherSql(sql);
    }

    /**
     * 查询记录列表
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    default String selectList(Map<String, Object> selectModel) {
        SQL sql = new SQL();
        sql.SELECT(createSelectSql(selectModel)
                .replace("SELECT", "").replace("select", ""));
        sql.FROM(createFromSql(selectModel)
                .replace("FROM", "").replace("from", ""));
        String whereSql = createWhereSql(selectModel)
                .replace("WHERE", "").replace("where", "");
        if (!StringUtils.isEmpty(whereSql)) {
            sql.WHERE(whereSql);
        }
        appendOtherSql(sql, selectModel);
        int page = Integer.getInteger((String) selectModel.get("page"), 0);
        int size = Integer.getInteger((String) selectModel.get("size"), 0);
        return sql.toString() + getPageSql(page, size);
    }

    /**
     * 查询记录列表计数
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    default String count(Map<String, Object> selectModel) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(*)");
        sql.FROM(createFromSql(selectModel)
                .replace("FROM", "").replace("from", ""));
        String whereSql = createWhereSql(selectModel)
                .replace("WHERE", "").replace("where", "");
        if (!StringUtils.isEmpty(whereSql)) {
            sql.WHERE(whereSql);
        }
        return sql.toString();
    }

    /**
     * 获取分页Sql
     *
     * @param page 页码
     * @param size 每页大小
     * @return Sql字符串
     */
    static String getPageSql(int page, int size) {
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
    static String getLikeSql(String keyword, String... jdbcFields) {
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
