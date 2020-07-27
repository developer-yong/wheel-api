package com.api.core;

import com.api.model.annotation.JDBCField;
import com.api.model.annotation.Table;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * 数据库操作Sql提供者基类
 *
 * @author coderyong
 */
public interface ISqlProvider<M> {

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    default String tableName() {
        //获取表名
        return loadModelClass().getAnnotation(Table.class).name();
    }

    /**
     * 表主键
     *
     * @return 主键名
     */
    default String primaryKey() {
        //获取表主键名
        return loadModelClass().getAnnotation(Table.class).primaryKey();
    }

    //****************************************** 插入、删除、更新数据 ******************************************//

    /**
     * 创建初始值
     *
     * @param m        记录对象
     * @param field    对象变量
     * @param isInsert 是否为插入操作
     */
    default void createInitValue(M m, Field field, boolean isInsert) throws IllegalAccessException {
        JDBCField jdbcField = field.getAnnotation(JDBCField.class);
        if (jdbcField != null) {
            //设置主键默认值
            if (jdbcField.name().equals(primaryKey())) {
                field.set(m, UUID.randomUUID().toString().replaceAll("-", ""));
            }
            //设置创建时间默认值
            if ("created_at".equals(jdbcField.name()) && isInsert) {
                if (field.getType() == Integer.class) {
                    field.set(m, (int) (System.currentTimeMillis() / 1000));
                } else {
                    field.set(m, System.currentTimeMillis());
                }
            }
            //设置更新时间默认值
            if ("updated_at".equals(jdbcField.name()) && !isInsert) {
                if (field.getType() == Integer.class) {
                    field.set(m, (int) (System.currentTimeMillis() / 1000));
                } else {
                    field.set(m, System.currentTimeMillis());
                }
            }
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
        for (Field field : m.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                createInitValue(m, field, true);
                JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                if (!ObjectUtils.isEmpty(field.get(m)) && jdbcField != null) {
                    sql.VALUES(jdbcField.name(), "#{" + field.getName() + "}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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
        sql.WHERE(primaryKey() + " IN (" + builder.toString() + ")");
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
                createInitValue(m, field, false);
                JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                if (!ObjectUtils.isEmpty(field.get(m)) && jdbcField != null) {
                    if (jdbcField.name().equals(primaryKey())) {
                        sql.WHERE(jdbcField.name() + " = #{" + field.getName() + "}");
                    } else {
                        sql.SET(jdbcField.name() + " = #{" + field.getName() + "}");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sql.toString();
    }


    //****************************************** 查询列表数据 ******************************************//

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    default String createSelectSql() {
        return "*";
    }

    /**
     * 创建FormSql
     *
     * @param parameter 查询参数对象
     * @return Sql字符串
     */
    default String createSelectSql(M parameter) {
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
     * @param parameter 查询参数对象
     * @return Sql字符串
     */
    default String createFromSql(M parameter) {
        return createFromSql();
    }

    /**
     * 创建查询条件Sql
     *
     * @param parameter 查询参数对象
     * @return 查询条件Sql
     */
    default String createWhereSql(M parameter) {
        return "";
    }


    /**
     * 拼接WHERE条件语句
     *
     * @param sql sql构建对象
     */
    default void appendWhereSql(SQL sql) {
    }

    /**
     * 拼接WHERE条件语句
     *
     * @param sql       sql构建对象
     * @param parameter 查询参数对象
     */
    default void appendWhereSql(SQL sql, M parameter) {
        appendWhereSql(sql);
    }

    /**
     * 附加其他Sql，比如ORDER BY、GROUP BY 等
     *
     * @param sql sql构建对象
     */
    default void appendOtherSql(SQL sql) {
    }

    /**
     * 附加其他Sql，比如ORDER BY、GROUP BY 等
     *
     * @param sql       sql构建对象
     * @param parameter 查询参数对象
     */
    default void appendOtherSql(SQL sql, M parameter) {
        appendOtherSql(sql);
    }

    /**
     * 查询记录列表
     *
     * @param parameter 查询参数对象
     * @return 查询Sql
     */
    default String selectList(M parameter) {
        SQL sql = new SQL();
        sql.SELECT(createSelectSql(parameter)
                .replace("SELECT", "").replace("select", ""));
        sql.FROM(createFromSql(parameter)
                .replace("FROM", "").replace("from", ""));
        String whereSql = createWhereSql(parameter)
                .replace("WHERE", "").replace("where", "");
        if (!StringUtils.isEmpty(whereSql)) {
            sql.WHERE(whereSql);
        }
        appendOtherSql(sql, parameter);
        if (parameter instanceof Parameter) {
            return sql.toString() + getPageSql(((Parameter) parameter).getPage(), ((Parameter) parameter).getSize());
        }
        return sql.toString();
    }

    /**
     * 查询记录列表计数
     *
     * @param parameter 查询参数对象
     * @return 查询Sql
     */
    default String countList(M parameter) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(*)");
        sql.FROM(createFromSql(parameter)
                .replace("FROM", "").replace("from", ""));
        String whereSql = createWhereSql(parameter)
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
