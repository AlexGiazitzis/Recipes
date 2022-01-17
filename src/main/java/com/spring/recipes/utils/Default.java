package com.spring.recipes.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A utility interface created to specify which constructor to be used by mapstruct when mapping objects.
 * @see <a href="url">https://mapstruct.org/documentation/stable/reference/html/#mapping-with-constructors</a>
 * @author Alex Giazitzis
 */
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.CLASS)
public @interface Default {

}
