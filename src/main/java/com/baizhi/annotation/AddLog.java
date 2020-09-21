package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//类型
@Target(ElementType.METHOD)
//生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface AddLog {
    String value();

    String name() default "";
}
