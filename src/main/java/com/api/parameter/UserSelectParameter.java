package com.api.parameter;

import com.api.core.SelectParameter;

/**
 * @author coderyong
 */
public class UserSelectParameter extends SelectParameter {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
