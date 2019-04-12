package com.api.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    @NotNull(message = "用户昵称不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度不能小于6位或大于18位")
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像
     */
    private byte[] icon;

    /**
     * 简介
     */
    private String introduction;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}