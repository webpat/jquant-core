package org.jquant.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Strategy Parameter
 * <p>
 * used in the BacktestLauncher tab and in Optimization 
 * @author patrick.merheb
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parameter {

	String category() default "";
	String description() default "";
}
