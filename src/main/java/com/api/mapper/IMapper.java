package com.api.mapper;


import com.api.model.annotation.JDBCField;
import com.api.model.annotation.TableName;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

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

    @SelectProvider(type = Provider.class, method = "selectById")
    T selectById(Class<T> clazz, String primaryKey);

    @SelectProvider(type = Provider.class, method = "selectAll")
    List<T> selectAll(Class<T> clazz);

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
                    if (field.get(record) != null && field.isAnnotationPresent(JDBCField.class)) {
                        JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                        sql.VALUES(jdbcField.name(), "#{" + field.getName() + ", jdbcType=" + jdbcField.type() + "}");
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
                    if (field.get(record) != null && field.isAnnotationPresent(JDBCField.class)) {
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

        /**
         * 查询单条记录
         *
         * @param clazz      记录对象
         * @param primaryKey 主键
         * @return 查询Sql语句
         */
        public String selectById(Class<?> clazz, String primaryKey) {
            if (!clazz.isAnnotationPresent(TableName.class)) {
                throw new IllegalArgumentException("Model 必须添加 TableName 注解");
            }
            SQL sql = new SQL();
            sql.SELECT("*").FROM(clazz.getAnnotation(TableName.class).value());

            String selectWhere = "";

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(JDBCField.class)) {
                    JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                    if (jdbcField.isIdentity()) {
                        selectWhere = jdbcField.name() + " = '" + primaryKey.replace(" ", "") + "'";
                        break;
                    }
                }
            }
            if (primaryKey != null && StringUtils.isEmpty(selectWhere)) {
                throw new IllegalArgumentException("查询失败，请确认查询条件是否正确");
            }
            sql.WHERE(selectWhere);
            return sql.toString();
        }

        /**
         * 查询所有记录
         *
         * @param clazz 记录对象类型
         * @return 查询Sql语句
         */
        public String selectAll(Class<?> clazz) {
            if (!clazz.isAnnotationPresent(TableName.class)) {
                throw new IllegalArgumentException("Model 必须添加 TableName 注解");
            }
            SQL sql = new SQL();
            sql.SELECT("*").FROM(clazz.getAnnotation(TableName.class).value());
            return sql.toString();
        }
    }
}
