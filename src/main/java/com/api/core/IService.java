package com.api.core;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author coderyong
 */
public interface IService<M, P> {

    /**
     * 保存单条记录
     *
     * @param ms 记录对象集合信息
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String save(M... ms) {
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
     * @param m 记录对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String update(M m) {
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
     * @param p 查询条件集合对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回单条记录结果
     */
    default Object findBy(P p) {
        return null;
    }

    /**
     * 查询记录列表
     *
     * @param p 查询条件集合对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回记录集合
     */
    default Object findListBy(P p) {
        return null;
    }

    /**
     * 查询记录列表总数
     *
     * @param p 查询条件集合对象信息
     * @return 记录集合总数
     */
    default int count(P p) {
        Object list = findListBy(p);
        return list != null && list instanceof List ? ((List) list).size() : 0;
    }

    /**
     * 获取主键值
     * <p>
     *      默认取 Model 中第一个包含有 Id 或者 id 的变量值，如果主键为特殊变量名需在实现类中重写此方法返回主键值
     * </P>
     *
     * @param m Model对象
     * @return 主键值
     */
    default String getPrimaryKey(M m) {
        String primaryKey = "";
        try {
            for (Field field : m.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().contains("Id") || field.getName().contains("id")) {
                    primaryKey = (String) field.get(m);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return primaryKey;
    }
}