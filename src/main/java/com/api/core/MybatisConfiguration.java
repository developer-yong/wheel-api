package com.api.core;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MybatisConfiguration {

    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.api.mapper");
        return mapperScannerConfigurer;
    }

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
            return new MapWrapper(metaObject, (Map) object) {
                @Override
                public String findProperty(String name, boolean useCamelCaseMapping) {
                    if (useCamelCaseMapping) {
                        //下划线转驼峰式
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < name.length(); i++) {
                            char c = name.charAt(i);
                            if (i > 0 && c == '_') {
                                sb.append(Character.toUpperCase(name.charAt(++i)));
                            } else {
                                sb.append(Character.toLowerCase(c));
                            }
                        }
                        return sb.toString();
                    }
                    return name;
                }
            };
        }
    }
}