package fr.atesab.atiancore.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * an annotation to say a field must be saved by parsing it with the
 * {@link ConfigurationLoader}
 * 
 * @author ATE47
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Config {
	/**
	 * @return the comment to write in the config file
	 */
	String comment() default "";

	/**
	 * @return the name to write in the config file, empty for the field name
	 */
	String name() default "";

	/**
	 * @return the path in the config file
	 */
	String path() default "general";

	/**
	 * @return the name translated of this config
	 */
	String nameTranslated() default "";
}
