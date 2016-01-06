package com.rybak.effective.java.ch6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Предпочитайте аннотации шаблонам присвоения имен
 */
//Marker annotation type declaration

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test
{
}
