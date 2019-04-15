package com.api.core;

import com.api.core.utils.Logger;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * @author coderyong
 */
public interface IProvider<M, P> {

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    default String tableName() {
        // 使用Model类名创建表名
        return camelToUnderline(loadModelClass().getSimpleName());
    }

    /**
     * 表主键在Model中的名称
     *
     * @return 字段名
     */
    default String primaryKeyInModelName() {
        String idName = "";
        try {
            for (Field field : loadModelClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().contains("Id") || field.getName().contains("id")) {
                    return field.getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idName;
    }

    //****************************************** 插入、删除、更新数据 ******************************************//

    default String createJDBCName(String fieldName) {
        return camelToUnderline(fieldName);
    }

    /**
     * 创建默认值
     *
     * @param m        记录对象
     * @param isInsert 是否为插入操作
     */
    default void createDefaultValue(M m, boolean isInsert) {
        try {
            Class<?> clazz = m.getClass();
            if (isInsert) {
                //设置主键的默认值
                Field primaryKeyField = clazz.getDeclaredField(primaryKeyInModelName());
                if (ObjectUtils.isEmpty(primaryKeyField.get(m))) {
                    primaryKeyField.setAccessible(true);
                    primaryKeyField.set(m, UUID.randomUUID().toString().replaceAll("-", ""));
                }
                //设置创建时间的默认值
                Field createdAtField = clazz.getDeclaredField("createdAt");
                if (ObjectUtils.isEmpty(createdAtField)) {
                    createdAtField.setAccessible(true);
                    createdAtField.set(m, System.currentTimeMillis() / 1000);
                }
            } else {
                //设置创建时间的默认值
                Field updatedAtField = clazz.getDeclaredField("updatedAt");
                if (ObjectUtils.isEmpty(updatedAtField)) {
                    updatedAtField.setAccessible(true);
                    updatedAtField.set(m, System.currentTimeMillis() / 1000);
                }
            }
        } catch (Exception e) {
            Logger.e("变量未定义", e);
        }
    }

    /**
     * 插入多条记录
     *
     * @param m 记录对象集合
     * @return 插入Sql语句
     */
    default String insert(M m) {
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName());
        createDefaultValue(m, true);
        for (Field field : m.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (!ObjectUtils.isEmpty(field.get(m))) {
                    sql.VALUES(createJDBCName(field.getName()), "#{" + field.getName() + "}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sql.toString();
    }

    /**
     * 插入多条记录
     *
     * @param ms 记录对象集合
     * @return 插入Sql语句
     */
    default String inserts(M... ms) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName());
        StringBuilder columns = new StringBuilder();
        columns.append(" (");
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < ms.length; i++) {
            M m = ms[i];
            values.append(i == 0 ? " (" : ", (");
            createDefaultValue(m, true);
            Field[] fields = m.getClass().getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                try {
                    String jdbcName = createJDBCName(field.getName());
                    if (i == 0) {
                        columns.append(j == 0 ? jdbcName : ", " + jdbcName);
                    }
                    if (j > 0) {
                        values.append(", ");
                    }
                    Object value = field.get(m);
                    if (value != null && field.getType() == String.class) {
                        values.append("'").append(field.get(m)).append("'");
                    } else if (value == null && field.getType() == Integer.class) {
                        values.append(0);
                    } else {
                        values.append(field.get(m));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            values.append(")");
        }
        columns.append(") ");
        sql.append(columns.toString());
        sql.append("VALUES ").append(values.toString());
        return sql.toString();
    }

    /**
     * 删除单条或多条记录
     *
     * @param primaryKeys 主键数组
     * @return 删除Sql语句
     */
    default String deleteByIds(String... primaryKeys) {
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName());
        //禁止主键数组为空删除操作
        if (ObjectUtils.isEmpty(primaryKeys)) {
            throw new IllegalArgumentException("删除失败，请确认删除条件是否正确");
        }
        StringBuilder builder = new StringBuilder();
        for (String primaryKey : primaryKeys) {
            if (!StringUtils.isEmpty(builder.toString())) {
                builder.append(", ");
            }
            builder.append("'").append(primaryKey).append("'");
        }
        sql.WHERE(createJDBCName(primaryKeyInModelName()) + " IN (" + builder.toString() + ")");
        return sql.toString();
    }

    /**
     * 更新记录
     *
     * @param m 记录对象
     * @return 更新Sql语句
     */
    default String update(M m) {
        SQL sql = new SQL();
        sql.UPDATE(tableName());
        for (Field field : m.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                createDefaultValue(m, false);
                if (!ObjectUtils.isEmpty(field.get(m))) {
                    String jdbcName = createJDBCName(field.getName());
                    if (primaryKeyInModelName().equals(field.getName())) {
                        sql.WHERE(jdbcName + " = #{" + field.getName() + "}");
                    } else {
                        sql.SET(jdbcName + " = #{" + field.getName() + "}");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sql.toString();
    }


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
     * @param p 查询需要的字段
     * @return Sql字符串
     */
    default String createSelectSql(P p) {
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
     * @param p 查询需要的字段
     * @return Sql字符串
     */
    default String createFromSql(P p) {
        return createFromSql();
    }

    /**
     * 创建查询条件Sql
     *
     * @param p 查询需要的字段
     * @return 查询条件Sql
     */
    default String createWhereSql(P p) {
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
     * @param p 查询需要的字段
     * @return 附加Sql
     */
    default void appendOtherSql(SQL sql, P p) {
        appendOtherSql(sql);
    }

    /**
     * 查询记录列表
     *
     * @param p 查询需要的字段
     * @return 查询Sql
     */
    default String selectList(P p) {
        SQL sql = new SQL();
        sql.SELECT(createSelectSql(p)
                .replace("SELECT", "").replace("select", ""));
        sql.FROM(createFromSql(p)
                .replace("FROM", "").replace("from", ""));
        String whereSql = createWhereSql(p)
                .replace("WHERE", "").replace("where", "");
        if (!StringUtils.isEmpty(whereSql)) {
            sql.WHERE(whereSql);
        }
        appendOtherSql(sql, p);
        if (p instanceof SelectParameter) {
            return sql.toString() + getPageSql(((SelectParameter) p).getPage(), ((SelectParameter) p).getSize());
        }
        return sql.toString();
    }

    /**
     * 查询记录列表计数
     *
     * @param p 查询需要的字段
     * @return 查询Sql
     */
    default String countList(P p) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(*)");
        sql.FROM(createFromSql(p)
                .replace("FROM", "").replace("from", ""));
        String whereSql = createWhereSql(p)
                .replace("WHERE", "").replace("where", "");
        if (!StringUtils.isEmpty(whereSql)) {
            sql.WHERE(whereSql);
        }
        return sql.toString();
    }

    /**
     * 驼峰式转下滑线字符串
     *
     * @param str 原字符串
     * @return 下划线字符串
     */
    static String camelToUnderline(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 获取分页Sql
     *
     * @param page 页码
     * @param size 每页大小
     * @return Sql字符串
     */
    static String getPageSql(Integer page, Integer size) {
        if (page != null && page > 0) {
            size = (size != null && size > 0) ? size : 20;
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

    default Class<?> loadModelClass() {
        // 获取当前new的对象的泛型的父类类型
        Type[] interfaces = this.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                // 获取第一个类型参数的真实类型
                return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        }
        return null;
    }
}
