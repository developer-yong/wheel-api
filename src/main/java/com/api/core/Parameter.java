package com.api.core;

import javax.validation.constraints.Min;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author coderyong
 */
public class Parameter extends HashMap<String, Object> {
    /**
     * 页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer page;
    /**
     * 页量
     */
    @Min(value = 0, message = "每页数量不能少于0条")
    private Integer size;
    /**
     * 关键字
     */
    private String keyword;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Object put(String key, Object value) {
        try {
            Field field = this.getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(this, value);
            return value;
        } catch (Exception ignored) {
            return super.put(key, value);
        }
    }

    @Override
    public Object get(Object key) {
        try {
            Field field = this.getClass().getDeclaredField(String.valueOf(key));
            field.setAccessible(true);
            return field.get(this);
        } catch (Exception ignored) {
            return super.get(key);
        }
    }
}
