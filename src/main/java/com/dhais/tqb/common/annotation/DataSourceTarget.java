package com.dhais.tqb.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @since 2021/2/26 16:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface DataSourceTarget {
    String value() default "default";
}
