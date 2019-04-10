package com.api.core.config;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.setObjectWrapperFactory(new MapWrapperFactory());
    }

    class MapWrapperFactory implements ObjectWrapperFactory {

        @Override
        public boolean hasWrapperFor(Object object) {
            return object != null && object instanceof Map;
        }

        @Override
        public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
            return new MapWrapper(metaObject, (Map) object){
                @Override
                public String findProperty(String name, boolean useCamelCaseMapping) {
                    if(useCamelCaseMapping){
                        //下划线转驼峰式
                        return underlineToCamelString(name);
                    }
                    return name;
                }
            };
        }
    }

    /**
     * 获取驼峰式字符串
     *
     * @param str 原字符串
     * @return 驼峰式字符串
     */
    private static String underlineToCamelString(String str) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

}