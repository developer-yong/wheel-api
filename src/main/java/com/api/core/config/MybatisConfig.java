package com.api.core.config;

import com.api.core.utils.MapUtils;
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
            return new MapWrapper(metaObject, (Map<String, Object>) object){
                @Override
                public String findProperty(String name, boolean useCamelCaseMapping) {
                    if(useCamelCaseMapping){
                        //下划线转驼峰式
                        return MapUtils.underlineToCamelString(name);
                    }
                    return name;
                }
            };
        }
    }

}