package org.knard.tools.autoSwing.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation used to specify application argument that junit should pass when
 * the application will be launched
 * 
 * @author knard
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface ApplicationArgument {
	String[] value();
}
