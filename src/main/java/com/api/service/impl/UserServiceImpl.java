package com.api.service.impl;

import com.api.mapper.UserMapper;
import com.api.model.User;
import com.api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
        return userMapper.deleteByIds(User.class, userId) > 0 ? "" : "删除失败";
    }

    @Override
    public String update(User user) {
        return userMapper.update(user) > 0 ? "" : "更新失败";
    }

    @Override
    public Object findById(String userId) {
        return findBy(new HashMap<String, Object>() {
            {
                put("userId", userId);
            }
        });
    }

    @Override
    public Object findBy(Map<String, Object> selectModel) {
        return userMapper.selectBy(selectModel);
    }

    @Override
    public Object findListBy(Map<String, Object> selectModel) {
        return userMapper.selectListBy(selectModel);
    }

    @Override
    public int count(Map<String, Object> selectModel) {
        return userMapper.count(selectModel);
    }
}

