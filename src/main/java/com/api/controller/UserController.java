package com.api.controller;

import com.api.controller.validator.PageParameter;
import com.api.model.User;
import com.api.service.IService;
import com.api.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController implements IController<User, PageParameter> {

    @Resource
    private UserService userService;

    @Override
    public IService<User> createService() {
        return userService;
    }

    @RequestMapping("/save")
    public Map<String, Object> save(@Valid User user, BindingResult result) {
        return IController.super.save(user, result);
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam String userId) {
        return IController.super.delete(userId);
    }

    @RequestMapping("/update")
    public Map<String, Object> update(@Valid User user, BindingResult result) {
        return IController.super.update(user, result);
    }

    @RequestMapping("/detail")
    public Map<String, Object> detail(String primaryKey) {
        return IController.super.detail(primaryKey);
    }

    @RequestMapping("/list")
    public Map<String, Object> list(@Valid PageParameter parameter, String keyword, BindingResult result) {
        return IController.super.list(parameter, keyword, result);
    }
}
