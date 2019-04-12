package com.api.service.impl;

import com.api.mapper.UserMapper;
import com.api.model.User;
import com.api.parameter.UserSelectParameter;
import com.api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String save(User user) {
        return userMapper.insert(user) > 0 ? "" : "保存失败";
    }

    @Override
    public String delete(String... userId) {
        return userMapper.deleteByIds(userId) > 0 ? "" : "删除失败";
    }

    @Override
    public String update(User user) {
        return userMapper.update(user) > 0 ? "" : "更新失败";
    }

    @Override
    public Object findById(String userId) {
        UserSelectParameter selectParameter = new UserSelectParameter();
        selectParameter.setUserId(userId);
        return findBy(selectParameter);
    }

    @Override
    public Object findBy(UserSelectParameter userSelectParameter) {
        return userMapper.selectBy(userSelectParameter);
    }

    @Override
    public Object findListBy(UserSelectParameter userSelectParameter) {
        return userMapper.selectListBy(userSelectParameter);
    }

    @Override
    public int count(UserSelectParameter userSelectParameter) {
        return userMapper.count(userSelectParameter);
    }
}

