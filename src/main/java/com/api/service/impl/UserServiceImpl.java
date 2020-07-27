package com.api.service.impl;

import com.api.mapper.UserMapper;
import com.api.model.User;
import com.api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User业务逻辑实现类
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * User数据库交互对象
     */
    @Resource
    private UserMapper userMapper;

    /**
     * User添加业务逻辑实现
     *
     * @param user User数据实体对象
     * @return User添加逻辑处理结果
     */
    @Override
    public String save(User user) {
        return userMapper.insert(user) > 0 ? "" : "添加失败";
    }

    /**
     * User删除业务逻辑实现
     *
     * @param userIds User主键标识数组
     * @return User删除逻辑处理结果
     */
    @Override
    public String delete(String... userIds) {
        return userMapper.deleteByIds(userIds) > 0 ? "" : "删除失败";
    }

    /**
     * User修改业务逻辑实现
     *
     * @param user User数据实体对象
     * @return User改逻辑处理结果
     */
    @Override
    public String update(User user) {
        return userMapper.update(user) > 0 ? "" : "修改失败";
    }

    /**
     * User详情查询业务逻辑实现
     *
     * @param userId User主键标识
     * @return User记录详情查询结果
     */
    @Override
    public User findById(String userId) {
        return userMapper.selectBy(new User(userId));
    }

    /**
     * User列表查询业务逻辑实现
     *
     * @param parameter 查询参数字段对象
     * @return User记录列表查询结果
     */
    @Override
    public List<User> findListBy(User parameter) {
        return userMapper.selectListBy(parameter);
    }

    /**
     * User列表数量统计业务逻辑实现
     *
     * @param parameter 查询参数字段对象
     * @return User记录列表数量
     */
    @Override
    public int count(User parameter) {
        return userMapper.count(parameter);
    }
}

