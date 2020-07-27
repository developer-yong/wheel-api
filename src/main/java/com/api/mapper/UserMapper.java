package com.api.mapper;

import com.api.mapper.provider.UserSqlProvider;
import com.api.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    /**
     * 插入单条User记录
     *
     * @param user User数据实体对象
     * @return 影响记录条数（0-失败，1-成功）
     */
    @InsertProvider(type = UserSqlProvider.class, method = "insert")
    int insert(User user);

    /**
     * 删除单条或多条User记录
     *
     * @param userIds User主键数组
     * @return 影响记录条数（0-失败，大于0-成功）
     */
    @DeleteProvider(type = UserSqlProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("primaryKeys") String... userIds);

    /**
     * 更新单条User记录
     *
     * @param user User数据实体对象
     * @return 影响记录条数（0-失败，1-成功）
     */
    @UpdateProvider(type = UserSqlProvider.class, method = "update")
    int update(User user);

    /**
     * 查询单条User记录
     *
     * @param parameter 查询条件参数对象
     * @return User记录结果
     */
    @SelectProvider(type = UserSqlProvider.class, method = "selectOne")
    User selectBy(User parameter);

    /**
     * 查询User记录列表
     *
     * @param parameter 查询条件参数对象
     * @return User记录列表结果
     */
    @SelectProvider(type = UserSqlProvider.class, method = "selectList")
    List<User> selectListBy(User parameter);

    /**
     * 统计User记录列表数量
     *
     * @param parameter 查询条件参数对象
     * @return User记录列表数量
     */
    @SelectProvider(type = UserSqlProvider.class, method = "countList")
    int count(User parameter);
}