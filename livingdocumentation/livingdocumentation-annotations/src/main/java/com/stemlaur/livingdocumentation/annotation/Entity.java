/**
 * 
 */
package com.stemlaur.livingdocumentation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents an entity. (object subject to a lifecycle with an identity)
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Entity {

    String brief() default "An Entity that acts as the root for a cluster of associated objects, all treated as a unit";

    String link() default "http://domaindrivendesign.org/node/88";
}
