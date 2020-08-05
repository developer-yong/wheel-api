package com.api.model;

import com.api.model.annotation.JDBCField;
import com.api.model.annotation.Table;
import javax.validation.constraints.NotEmpty;

@Table(name = "user", primaryKey = "user_id")
public class User {
    /**
     * 用户ID
     */
    @JDBCField(name = "user_id", type = "VARCHAR")
    private String userId;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    @JDBCField(name = "username", type = "VARCHAR")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @JDBCField(name = "password", type = "VARCHAR")
    private String password;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    @JDBCField(name = "email", type = "VARCHAR")
    private String email;

    /**
     * 手机
     */
    @NotEmpty(message = "手机不能为空")
    @JDBCField(name = "phone", type = "VARCHAR")
    private String phone;

    /**
     * 部门
     */
    @NotEmpty(message = "部门不能为空")
    @JDBCField(name = "department", type = "VARCHAR")
    private String department;

    /**
     * 用户描述
     */
    @JDBCField(name = "user_description", type = "VARCHAR")
    private String userDescription;

    /**
     * 状态（0关闭，1正常）
     */
    @JDBCField(name = "status", type = "INTEGER", defaultValue = "1")
    private Integer status;

    /**
     * 创建时间
     */
    @JDBCField(name = "created_at", type = "BIGINT", defaultValue = "0")
    private Long createdAt;

    /**
     * 更新时间
     */
    @JDBCField(name = "updated_at", type = "BIGINT", defaultValue = "0")
    private Long updatedAt;

    public User() {
        
    }

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}