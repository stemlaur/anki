/**
 * 
 */
package com.stemlaur.livingdocumentation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a domain event.
 * Captures the memory of something interesting which affects the domain.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DomainEvent {

    String brief() default "Represents a Domain Event";

    String[] link() default {
            "http://martinfowler.com/eaaDev/DomainEvent.html" };
}
