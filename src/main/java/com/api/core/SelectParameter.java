package com.api.core;

import javax.validation.constraints.Min;

/**
 * @author coderyong
 */
public class SelectParameter {
    @Min(value = 1, message = "页码不能小于1")
    private Integer page;
    @Min(value = 1, message = "每页数量必须多余0条")
    private Integer size;

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
}
