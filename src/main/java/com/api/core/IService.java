package com.api.core;

import com.api.model.annotation.Table;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Stream;

/**
 * 业务逻辑处理基类
 *
 * @author coderyong
 */
public interface IService<M> {

    /**
     * 添加单条记录
     *
     * @param m 记录字典对象
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String save(M m) {
        return null;
    }

    /**
     * 添加多条记录
     *
     * @param ms 记录字典对象数组
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String save(M... ms) {
        StringBuilder messages = new StringBuilder();
        Stream.of(ms)
                .parallel()
                .forEach(m -> {
                    if (!StringUtils.isEmpty(messages.toString())) {
                        messages.append(",");
                    }
                    messages.append(save(m));
                });
        return messages.toString();
    }

    /**
     * 删除单条或多条记录
     *
     * @param primaryKeys 记录主键数组
     * @return 如果有错误信息返回错误提示信息，否则返回空或空字符串
     */
    @Transactional
    default String delete(String... primaryKeys) {
        return null;
    }

    /**
     * 修改单条记录
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
    default M findById(String primaryKey) {
        return null;
    }

    /**
     * 查询记录列表
     *
     * @param parameter 查询条件集合对象信息
     * @return 如果有错误信息返回错误提示信息，否则返回记录集合
     */
    default List<M> findListBy(M parameter) {
        return null;
    }

    /**
     * 查询记录列表总数
     *
     * @param parameter 查询条件集合对象信息
     * @return 记录集合总数
     */
    default int count(M parameter) {
        return 0;
    }

    /**
     * 获取主键值
     * <p>
     * 默认取 Model 中第一个包含有 Id 或者 id 的变量值，如果主键为特殊变量名需在实现类中重写此方法返回主键值
     * </P>
     *
     * @param m 记录字典对象
     * @return 主键值
     */
    default String getIdName(M m) {
        String primaryKey = m.getClass().getAnnotation(Table.class).primaryKey();
        StringBuilder idName = new StringBuilder();
        for (int i = 0; i < primaryKey.length(); i++) {
            char c = primaryKey.charAt(i);
            if (c == '_') {
                i++;
                c = Character.toUpperCase(primaryKey.charAt(i));
            }
            idName.append(c);
        }
        return idName.toString();
    }
}
