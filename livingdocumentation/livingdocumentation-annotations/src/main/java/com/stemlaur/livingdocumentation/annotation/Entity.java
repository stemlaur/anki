/**
 * 
 */
package com.stemlaur.livingdocumentation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents an entity.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Entity {
}
