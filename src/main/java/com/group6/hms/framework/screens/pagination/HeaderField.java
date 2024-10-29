package com.group6.hms.framework.screens.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HeaderField {
    boolean show() default true;
    String name() default "";
    int width() default 20;
    Class<? extends FieldRenderer> renderer() default StringRenderer.class;
}