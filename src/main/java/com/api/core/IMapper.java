package com.api.core;


import com.api.core.annotation.JDBCField;
import com.api.core.annotation.TableName;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author coderyong
 */
public interface IMapper<T> {

    @InsertProvider(type = Provider.class, method = "insert")
    int insert(T record);

    @DeleteProvider(type = Provider.class, method = "deleteByIds")
    int deleteByIds(Class<T> clazz, String... ids);

    @UpdateProvider(type = Provider.class, method = "update")
    int update(T record);

    class Provider {

        /**
         * 更新记录
         *
         * @param record 记录对象
         * @return 更新Sql语句
         */
        public String insert(Object record) {
            Class<?> clazz = record.getClass();
            if (!clazz.isAnnotationPresent(TableName.class)) {
                throw new IllegalArgumentException("Model 必须添加 TableName 注解");
            }
            SQL sql = new SQL();
            sql.INSERT_INTO(clazz.getAnnotation(TableName.class).value());

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if ("createdAt".equals(field.getName())) {
                        field.set(record, System.currentTimeMillis() / 1000);
                    }
                    if (field.isAnnotationPresent(JDBCField.class)) {
                        JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                        if (jdbcField.isIdentity() && StringUtils.isEmpty(field)) {
                            field.set(record, UUID.randomUUID().toString().replaceAll("-", ""));
                        }
                        if (!ObjectUtils.isEmpty(field.get(record))) {
                            sql.VALUES(jdbcField.name(), "#{" + field.getName() + ", jdbcType=" + jdbcField.type() + "}");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return sql.toString();
        }

        /**
         * 删除记录
         *
         * @param clazz       记录对象类型
         * @param primaryKeys 主键数组
         * @return 删除Sql语句
         */
        public String deleteByIds(Class<?> clazz, String... primaryKeys) {
            if (!clazz.isAnnotationPresent(TableName.class)) {
                throw new IllegalArgumentException("Model 必须添加 TableName 注解");
            }
            SQL sql = new SQL();
            sql.DELETE_FROM(clazz.getAnnotation(TableName.class).value());

            String deleteWhere = "";

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(JDBCField.class)) {
                    JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                    if (jdbcField.isIdentity()) {
                        StringBuilder builder = new StringBuilder();
                        for (String primaryKey : primaryKeys) {
                            if (!StringUtils.isEmpty(builder.toString())) {
                                builder.append(", ");
                            }
                            builder.append("'").append(primaryKey).append("'");
                        }
                        deleteWhere = jdbcField.name() + " in (" + builder.toString() + ")";
                        break;
                    }
                }
            }
            if (primaryKeys != null && StringUtils.isEmpty(deleteWhere)) {
                throw new IllegalArgumentException("删除失败，请确认删除条件是否正确");
            }
            sql.WHERE(deleteWhere);
            return sql.toString();
        }

        /**
         * 更新记录
         *
         * @param record 记录对象
         * @return 更新Sql语句
         */
        public String update(Object record) {
            Class<?> clazz = record.getClass();
            if (!clazz.isAnnotationPresent(TableName.class)) {
                throw new IllegalArgumentException("Model 必须添加 TableName 注解");
            }
            SQL sql = new SQL();
            sql.UPDATE(clazz.getAnnotation(TableName.class).value());

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if ("updatedAt".equals(field.getName())) {
                        field.set(record, System.currentTimeMillis() / 1000);
                    }
                    if (!ObjectUtils.isEmpty(field.get(record)) && field.isAnnotationPresent(JDBCField.class)) {
                        JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                        if (jdbcField.isIdentity()) {
                            sql.WHERE(jdbcField.name() + " = #{" + field.getName() + ", jdbcType=" + jdbcField.type() + "}");
                        } else {
                            sql.SET(jdbcField.name() + " = #{" + field.getName() + ", jdbcType=" + jdbcField.type() + "}");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return sql.toString();
        }
    }
}
