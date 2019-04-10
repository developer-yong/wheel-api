package com.api.core.config;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @author coderyong
 */
public class ConfigProperties {
    /**
     * 上传地址
     */
    private String filePath;
    /**
     * 头像地址
     */
    private String iconPath;

    public String getFilePath() {
        try {
            return ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX)
                    .getPath() + (filePath.startsWith("/") ? filePath : "/" + filePath);
        } catch (FileNotFoundException e) {
            return filePath;
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIconPath() {
        try {
            return ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX)
                    .getPath() + (iconPath.startsWith("/") ? iconPath : "/" + iconPath);
        } catch (FileNotFoundException e) {
            return iconPath;
        }
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "filePath='" + filePath + '\'' +
                ", iconPath='" + iconPath + '\'' +
                '}';
    }
}
