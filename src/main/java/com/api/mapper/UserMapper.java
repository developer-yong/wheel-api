package com.api.mapper;

import com.api.mapper.provider.UserSelectProvider;
import com.api.model.User;
import com.api.parameter.UserSelectParameter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    /**
     * 插入单条记录
     *
     * @param user 插入对象
     * @return 影响数据条数
     */
    @InsertProvider(type = UserSelectProvider.class, method = "insert")
    int insert(User user);

    /**
     * 插入多条记录
     *
     * @param users 插入对象数组
     * @return 影响数据条数
     */
    @InsertProvider(type = UserSelectProvider.class, method = "inserts")
    int inserts(@Param("arg0") User... users);

    /**
     * 删除单条或多条记录
     *
     * @param userIds 主键数组
     * @return 影响数据条数
     */
    @DeleteProvider(type = UserSelectProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("arg0") String... userIds);

    /**
     * 更新单条记录
     *
     * @param user 更新对象
     * @return 影响数据条数
     */
    @UpdateProvider(type = UserSelectProvider.class, method = "update")
    int update(User user);

    /**
     * 查询单条记录
     *
     * @param userSelectParameter 查询条件集合对象信息
     * @return 单条记录结果
     */
    @SelectProvider(type = UserSelectProvider.class, method = "selectOne")
    Map<String, Object> selectBy(UserSelectParameter userSelectParameter);

    /**
     * 查询记录列表
     *
     * @param userSelectParameter 查询条件集合对象信息
     * @return 记录集合
     */
    @SelectProvider(type = UserSelectProvider.class, method = "selectList")
    List<Map<String, Object>> selectListBy(UserSelectParameter userSelectParameter);

    /**
     * 获取记录数量
     *
     * @param userSelectParameter 查询条件集合对象信息
     * @return 记录数量
     */
    @SelectProvider(type = UserSelectProvider.class, method = "countList")
    int count(UserSelectParameter userSelectParameter);
}