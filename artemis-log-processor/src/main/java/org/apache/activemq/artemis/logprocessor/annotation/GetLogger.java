package org.apache.activemq.artemis.logprocessor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** This tells the processor this method should return the Logger used by the instance */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface GetLogger {

}
