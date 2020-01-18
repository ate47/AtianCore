/**
 * 
 */
package fr.atesab.atiancore.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * say this class can be configure
 * 
 * @author ATE47
 *
 */
public @interface Configurable {

}
