package com.rybak.effective.java.ch6;

import java.lang.annotation.*;

/**
 * Annotation type with parameter
 *
 * Indicates that the annotated method is a test method that
 * must throw the designated exception to succeed
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest
{
    Class<? extends Exception>[] value();
}
