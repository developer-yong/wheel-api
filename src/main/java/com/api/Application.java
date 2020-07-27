package com.api;

import com.api.core.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author coderyong
 */
@SpringBootApplication
public class Application extends WebMvcConfigurationSupport implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext sContext;

    public static void main(String[] args) {
        sContext = SpringApplication.run(Application.class, args);
    }

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册处
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        RequestInterceptor.printHeaders = false;
        registry.addInterceptor(new RequestInterceptor());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        sContext = applicationContext;
        super.setApplicationContext(applicationContext);
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getContext() {
        return sContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name 类名
     * @return Object
     */
    public static Object getBean(String name) {
        return getContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz 类class
     * @param <T>   类类型
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean.
     *
     * @param name  类名
     * @param clazz 类class
     * @param <T>   类类型
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getContext().getBean(name, clazz);
    }
}