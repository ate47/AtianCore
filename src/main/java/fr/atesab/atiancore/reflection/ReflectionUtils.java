package fr.atesab.atiancore.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import fr.atesab.atiancore.utils.Tuple;

/**
 * Spigot tools for reflection and maths
 * 
 * @author ATE47
 *
 */
public class ReflectionUtils {

	/**
	 * execute for each field of a type in an object an action
	 * 
	 * @param type
	 *            the type to find
	 * @param obj
	 *            the object where search
	 * @param action
	 *            the action to do
	 */
	@SuppressWarnings("unchecked")
	public static <T> void forEachFieldOfTypeInto(Class<T> type, Object obj, Consumer<T> action) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.getType() == type)
				try {
					action.accept((T) f.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
		}
	}

	public static <T extends Annotation> void forEachFieldWithAnnotation(Class<T> annotation, Class<?> type,
			BiConsumer<T, Field> action) {
		if (type == Object.class)
			return;

		for (Field f : type.getDeclaredFields()) {
			T an = f.getAnnotation(annotation);
			if (an != null) {
				action.accept(an, f);
			}
		}
		forEachFieldWithAnnotation(annotation, type.getSuperclass(), action);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> Tuple<T, Field>[] getAllFieldWithAnnotation(Class<T> annotation,
			Class<?> type) {
		List<Tuple<T, Field>> f = new ArrayList<>();
		forEachFieldWithAnnotation(annotation, type, (an, fi) -> f.add(new Tuple<T, Field>(an, fi)));
		return f.toArray(new Tuple[f.size()]);
	}

	/**
	 * search a field of a type in an object an action
	 * 
	 * @param type
	 *            the type to find
	 * @param obj
	 *            the object where search
	 * @return the first if exists, null otherwise
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFirstFieldOfTypeInto(Class<T> type, Object obj) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.getType() == type)
				try {
					return (T) f.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return null;
				}
		}
		return null;
	}

	/**
	 * Get a Method without parameters from an object from name
	 */
	public static Object getMethod(String name, Object object) throws Exception {
		return getMethod(name, object, new Class<?>[] {}, new Object[] {});
	}

	/**
	 * Get a Method with parameters from an object from name, parameterTypes and
	 * parameters
	 */
	public static Object getMethod(String name, Object object, Class<?>[] parameterTypes, Object[] parameters)
			throws Exception {
		return object.getClass().getMethod(name, parameterTypes).invoke(object, parameters);
	}

	/**
	 * get primitive parameter class from a array of parameters
	 */
	public static Class<?>[] getPrivimitiveParameterTypes(Object... parameters) {
		Class<?>[] parameterTypes = new Class<?>[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].getClass().equals(Double.class))
				parameterTypes[i] = double.class;
			else if (parameters[i].getClass().equals(Integer.class))
				parameterTypes[i] = int.class;
			else if (parameters[i].getClass().equals(Long.class))
				parameterTypes[i] = long.class;
			else if (parameters[i].getClass().equals(Short.class))
				parameterTypes[i] = short.class;
			else if (parameters[i].getClass().equals(Byte.class))
				parameterTypes[i] = byte.class;
			else if (parameters[i].getClass().equals(Boolean.class))
				parameterTypes[i] = boolean.class;
			else if (parameters[i].getClass().equals(Float.class))
				parameterTypes[i] = float.class;
			else if (parameters[i].getClass().equals(Character.class))
				parameterTypes[i] = char.class;
			else
				parameterTypes[i] = parameters[i].getClass();
		}
		return parameterTypes;
	}

}
