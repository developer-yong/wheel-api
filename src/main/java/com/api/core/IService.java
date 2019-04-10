package com.api.core;

import com.api.core.annotation.JDBCField;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author coderyong
 */
public interface IService<T> {

    /**
     * 保存单条记录
     *
     * @param t 记录对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String save(T t) {
        return null;
    }

    /**
     * 删除记录
     *
     * @param primaryKeys 记录主键数组
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String delete(String... primaryKeys) {
        return null;
    }

    /**
     * 更新单条记录
     *
     * @param t 记录对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String update(T t) {
        return null;
    }

    /**
     * 查询单条记录
     *
     * @param primaryKey 记录主键
     * @return 如果有错误信息返回错误提示信息，否则返回单条记录结果
     */
    default Object findById(String primaryKey) {
        return null;
    }

    /**
     * 查询单条记录
     *
     * @param selectModel 查询条件集合对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回单条记录结果
     */
    default Object findBy(Map<String, Object> selectModel) {
        return null;
    }

    /**
     * 查询记录列表
     *
     * @param selectModel 查询条件集合对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回记录集合
     */
    default Object findListBy(Map<String, Object> selectModel) {
        return null;
    }

    /**
     * 查询记录列表总数
     *
     * @param selectModel 查询条件集合对象信息
     * @return 记录集合总数
     */
    default int count(Map<String, Object> selectModel) {
        Object list = findListBy(selectModel);
        return list != null && list instanceof List ? ((List) list).size() : 0;
    }

    static <T> String getPrimaryKey(Class<T> clazz) {
        String primaryKey = "";
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(JDBCField.class)) {
                    JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                    if (jdbcField.isIdentity()) {
                        primaryKey = (String) field.get(clazz.newInstance());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return primaryKey;
    }
}