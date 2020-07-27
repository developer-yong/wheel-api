package com.api.model.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JDBCField {

    String name();

    String type();

    boolean notNull() default false;

    String defaultValue() default "";
}