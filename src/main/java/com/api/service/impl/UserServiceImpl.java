package com.api.service.impl;

import com.api.mapper.UserMapper;
import com.api.model.User;
import com.api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean delete(String... userId) {
        return userMapper.delete(User.class, userId) > 0;
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public User findById(String userId) {
        return userMapper.selectOne(User.class, userId);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll(User.class);
    }

    @Override
    public Map<String, Object> findBy(Map<String, Object> selectModel) {
        return userMapper.selectBy(selectModel);
    }

    @Override
    public List<Map<String, Object>> findListBy(Map<String, Object> selectModel) {
        return userMapper.selectListBy(selectModel);
    }

    @Override
    public int count(Map<String, Object> selectModel) {
        return userMapper.count(selectModel);
    }
}

