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
	public static <T> void forEachFieldOfTypeInto(Class<T> type, Object obj, Consumer<T> action) {
		new ReflectedObject(obj).forEachDeclaredObjectOfType(type, action);
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
			parameterTypes[i] = primitiveType(parameters[i].getClass());
		}
		return parameterTypes;
	}

	/**
	 * get the object type for an object
	 * 
	 * @param type
	 *            the type
	 * @return the non primitive type if primitive
	 */
	public static Class<?> primitiveType(Class<?> type) {
		if (type.equals(Double.class))
			return double.class;
		else if (type.equals(Integer.class))
			return int.class;
		else if (type.equals(Long.class))
			return long.class;
		else if (type.equals(Short.class))
			return short.class;
		else if (type.equals(Byte.class))
			return byte.class;
		else if (type.equals(Boolean.class))
			return boolean.class;
		else if (type.equals(Float.class))
			return float.class;
		else if (type.equals(Character.class))
			return char.class;
		else
			return type;
	}

	/**
	 * get the object type for an object
	 * 
	 * @param type
	 *            the type
	 * @return the non primitive type if primitive
	 */
	public static Class<?> unprimitiveType(Class<?> type) {
		if (type.isPrimitive()) {
			if (type == int.class)
				return Integer.class;
			if (type == short.class)
				return Short.class;
			if (type == long.class)
				return Long.class;
			if (type == double.class)
				return Double.class;
			if (type == float.class)
				return Float.class;
			if (type == char.class)
				return Character.class;
			if (type == byte.class)
				return Byte.class;
			if (type == boolean.class)
				return Boolean.class;
		}
		return type;
	}

}
