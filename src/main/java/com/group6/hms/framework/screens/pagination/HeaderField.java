package com.group6.hms.framework.screens.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to customize the display of fields in the {@code PaginationTableScreen}.
 * This annotation is applied to fields to control their visibility,
 * display name, width, and rendering behavior through a custom renderer {@link FieldRenderer}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HeaderField {
    /**
     * Control whether the field should be displayed
     * @return true if the value is to be displayed, false otherwise
     */
    boolean show() default true;
    /**
     * Specifies a custom name for the annotated field.
     * If not provided, the default name will be derived from the field's name.
     *
     * @return the custom name for the field as a string
     */
    String name() default "";
    /**
     * Specifies the width of the column in the table for the annotated field.
     * If not provided, the default width is 20.
     *
     * @return the width of the field as an integer
     */
    int width() default 20;
    /**
     * Specifies a custom renderer class for the annotated field.
     * The custom renderer class must extend from {@code FieldRenderer}.
     *
     * @return the class of the custom field renderer
     */
    Class<? extends FieldRenderer> renderer() default StringRenderer.class;
}