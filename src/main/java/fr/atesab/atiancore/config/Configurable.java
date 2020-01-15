package fr.atesab.atiancore.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * use to set a deep scan into a Type with {@link Config} annotation
 * 
 * @author ATE47
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Configurable {
}
