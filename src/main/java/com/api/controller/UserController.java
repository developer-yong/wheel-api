package com.api.controller;

import com.api.core.IController;
import com.api.core.IService;
import com.api.core.Parameter;
import com.api.core.Response;
import com.api.model.User;
import com.api.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * User接口管理
 */
@RestController
@RequestMapping("/api/user")
public class UserController implements IController<User> {

    /**
     * User业务逻辑处理实现对象
     */
    @Resource
    private UserService userService;

    @Override
    public IService<User> createService() {
        return userService;
    }

    /**
     * 添加User
     *
     * @param user User数据实体对象
     * @param result 字段校验绑定结果对象
     * @return User添加响应结果
     */
    @PostMapping("/save")
    public Response<User> save(@Valid User user, BindingResult result) {
        return IController.super.save(user, result);
    }

    /**
     * 删除User
     *
     * @param userId User主键标识（支持数组）
     * @return User删除响应结果
     */
    @GetMapping("/delete")
    public Response<String> delete(@RequestParam String... userId) {
        return IController.super.delete(userId);
    }

    /**
     * 修改User
     *
     * @param user User数据实体对象
     * @param result 字段校验绑定结果对象
     * @return User修改响应结果
     */
    @PostMapping("/update")
    public Response<String> update(@Valid User user, BindingResult result) {
        return IController.super.update(user, result);
    }

    /**
     * 查询User详情
     *
     * @param userId User主键标识
     * @return User详情查询响应结果
     */
    @GetMapping("/detail")
    public Response<User> detail(String userId) {
        return IController.super.detail(userId);
    }

    /**
     * 查询User列表
     *
     * @param parameter 查询参数字段对象
     * @param result    字段校验绑定结果对象
     * @return User列表查询响应结果
     */
    @GetMapping("/list")
    public Response<List<User>> list(@Valid User parameter, BindingResult result) {
        return IController.super.list(parameter, result);
    }
}
