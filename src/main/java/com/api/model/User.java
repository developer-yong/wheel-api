package com.api.model;

import com.api.model.annotation.JDBCField;
import com.api.model.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@TableName(value = "user")
public class User {
    /**
     * 用户ID
     */
    @JDBCField(name = "user_id", type = "VARCHAR", isIdentity = true)
    private String userId;

    /**
     * 昵称
     */
    @NotNull(message = "用户昵称不能为空")
    @JDBCField(name = "nickname", type = "VARCHAR", isIdentity = true)
    private String nickname;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度不能小于6位或大于18位")
    @JDBCField(name = "password", type = "VARCHAR", isIdentity = true)
    private String password;

    /**
     * 年龄
     */
    @JDBCField(name = "age", type = "INTEGER")
    private Integer age;

    /**
     * 生日
     */
    @JDBCField(name = "birthday", type = "DATE")
    private Date birthday;

    /**
     * 头像
     */
    @JDBCField(name = "icon", type = "LONGVARBINARY")
    private byte[] icon;

    /**
     * 简介
     */
    @JDBCField(name = "introduction", type = "LONGVARCHAR")
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