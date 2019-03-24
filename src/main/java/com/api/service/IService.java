package com.api.service;

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
     */
    default boolean save(T t) {
        return false;
    }

    /**
     * 删除记录
     *
     * @param primaryKeys 记录主键数组
     */
    default boolean delete(String... primaryKeys) {
        return false;
    }

    /**
     * 更新单条记录
     *
     * @param t 记录对象信息
     */
    default boolean update(T t) {
        return false;
    }

    /**
     * 查询单条记录
     *
     * @param primaryKey 记录主键
     * @return 单条记录结果
     */
    default T findById(String primaryKey) {
        return null;
    }

    /**
     * 查询单条记录
     *
     * @param selectModel 查询条件集合对象信息
     * @return 单条记录结果
     */
    default Map<String, Object> findBy(Map<String, Object> selectModel) {
        return null;
    }

    /**
     * 查询所有记录
     *
     * @return 所有记录集合
     */
    default List<T> findAll() {
        return null;
    }

    /**
     * 查询记录列表
     *
     * @param selectModel 查询条件集合对象信息
     * @return 记录集合
     */
    default List<Map<String, Object>> findListBy(Map<String, Object> selectModel) {
        return null;
    }

    /**
     * 查询记录列表
     *
     * @param selectModel 查询条件集合对象信息
     * @return 记录集合
     */
    default int count(Map<String, Object> selectModel) {
        return findListBy(selectModel) != null ? findListBy(selectModel).size() : 0;
    }
}