/**
 *
 */
package com.stemlaur.livingdocumentation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a value object.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ValueObject {
}
