package fr.atesab.atiancore.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * A class to store an object and his class without knowing his type
 * 
 * @author ATE47
 *
 */
public class ReflectedObject {
	private Class<?> type;
	private Object object;

	public ReflectedObject(Class<?> type) {
		this.type = type;
		this.object = null;
	}

	public ReflectedObject(Class<?> type, Object object) {
		this.type = type;
		this.object = object;
	}

	public ReflectedObject(Object object) {
		this.type = object.getClass();
		this.object = object;
	}

	public ReflectedObject(String type, Object object) throws ClassNotFoundException {
		this(Class.forName(type), object);
	}

	/**
	 * call a declared method of this object with parameters
	 */
	public ReflectedObject callDeclaredMethod(String name, Object... parameters) {
		Method method;
		Class<?> cls = getType();
		Exception e = null;
		do {
			try {
				method = cls.getDeclaredMethod(name, ReflectionUtils.getPrivimitiveParameterTypes(parameters));
				try {
					method.setAccessible(true);
					return new ReflectedObject(method.getReturnType(), method.invoke(object, parameters));
				} catch (Exception e1) {
					e1.printStackTrace();
					return new ReflectedObject(method.getReturnType(), null);
				}
			} catch (Exception e1) {
				if (e == null)
					e = e1;
			}
			cls = cls.getSuperclass();
		} while (cls != Object.class);
		e.printStackTrace();
		return new ReflectedObject(new Object());
	}

	/**
	 * call a declared method of this object with parameters and their types
	 */
	public ReflectedObject callDeclaredMethodWithTypes(String name, Class<?>[] parametertypes, Object[] parameters) {
		Class<?> cls = getType();
		Exception e = null;
		do {
			try {
				Method method = cls.getDeclaredMethod(name, parametertypes);
				method.setAccessible(true);
				return new ReflectedObject(method.getReturnType(), method.invoke(object, parameters));
			} catch (Exception e1) {
				if (e == null)
					e = e1;
			}
			cls = cls.getSuperclass();
		} while (cls != Object.class);
		e.printStackTrace();
		return new ReflectedObject(new Object());
	}

	/**
	 * call a method of this object with parameters
	 */
	public ReflectedObject callMethod(String name, Object... parameters) {
		Method method;
		try {
			method = getType().getMethod(name, ReflectionUtils.getPrivimitiveParameterTypes(parameters));
			try {
				method.setAccessible(true);
				return new ReflectedObject(method.getReturnType(), method.invoke(object, parameters));
			} catch (Exception e) {
				e.printStackTrace();
				return new ReflectedObject(method.getReturnType(), null);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ReflectedObject(new Object());
		}
	}

	/**
	 * call a method of this object with parameters and their types
	 */
	public ReflectedObject callMethodWithTypes(String name, Class<?>[] parametertypes, Object[] parameters) {
		try {
			Method method = getType().getMethod(name, parametertypes);
			method.setAccessible(true);
			return new ReflectedObject(method.getReturnType(), method.invoke(object, parameters));
		} catch (Exception e) {
			e.printStackTrace();
			return new ReflectedObject(new Object());
		}
	}

	/**
	 * create a new {@link ReflectedObject} cast with a class
	 */
	public ReflectedObject cast(Class<?> type) {
		return new ReflectedObject(type, type.cast(object));
	}

	/**
	 * consumer a consumer on every declared field of this class and every super
	 * classes
	 * 
	 * @param action
	 *            the action to do
	 */
	public void forEachDeclaredField(Consumer<Field> action) {
		forEachSuperClass(cls -> Arrays.asList(cls.getDeclaredFields()).forEach(action));
	}

	/**
	 * consume a consumer on every field value of a certain type
	 * 
	 * @param type
	 *            the type to search
	 * @param action
	 *            the action to do
	 */
	@SuppressWarnings("unchecked")
	public <T> void forEachDeclaredObjectOfType(Class<T> type, Consumer<T> action) {
		forEachDeclaredField(field -> {
			if (type.isAssignableFrom(field.getType()))
				try {
					field.setAccessible(true);
					action.accept((T) field.get(action));
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
		});
	}

	/**
	 * consume a consumer on this class and every super classes without counting the
	 * {@link Object} class
	 * 
	 * @param action
	 *            the action to do
	 */
	public void forEachSuperClass(Consumer<Class<?>> action) {
		Class<?> type = this.type;
		while (type != Object.class) {
			action.accept(type);
			type = type.getSuperclass();
		}

	}

	/**
	 * get this object as a boolean, return false if null or not a boolean
	 */
	public boolean getBoolean() {
		return object == null ? false : object instanceof Boolean ? ((Boolean) object).booleanValue() : false;
	}

	/**
	 * get this object as a byte, return 0 if null or not a Number
	 */
	public byte getByte() {
		return object == null ? 0 : object instanceof Number ? ((Number) object).byteValue() : 0;
	}

	/**
	 * get this object as a char, return '\0' if null or not a Character
	 */
	public char getChar() {
		return object == null ? '\0' : object instanceof Character ? ((Character) object).charValue() : '\0';
	}

	/**
	 * get a declared field of this object
	 */
	public ReflectedObject getDeclaredField(String name) {
		Class<?> cls = getType();
		Exception e = null;
		do {
			try {
				Field field = type.getDeclaredField(name);
				field.setAccessible(true);
				return new ReflectedObject(field.get(object));
			} catch (Exception e1) {
				if (e == null)
					e = e1;
			}
			cls = cls.getSuperclass();
		} while (cls != Object.class);
		e.printStackTrace();
		return new ReflectedObject(new Object());
	}

	/**
	 * get this object as a double, return 0 if null or not a Number
	 */
	public double getDouble() {
		return object == null ? 0 : object instanceof Number ? ((Number) object).doubleValue() : 0;
	}

	/**
	 * get a field of this object
	 */
	public ReflectedObject getField(String name) {
		try {
			Field field = getType().getField(name);
			field.setAccessible(true);
			return new ReflectedObject(field.get(object));
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ReflectedObject(new Object());
		}
	}

	/**
	 * get this object as a float, return 0 if null or not a Number
	 */
	public float getFloat() {
		return object == null ? 0 : object instanceof Number ? ((Number) object).floatValue() : 0;
	}

	/**
	 * get this object as a integer, return 0 if null or not a Number
	 */
	public int getInteger() {
		return object == null ? 0 : object instanceof Number ? ((Number) object).intValue() : 0;
	}

	/**
	 * get this object as a long, return 0 if null or not a Number
	 */
	public long getLong() {
		return object == null ? 0 : object instanceof Number ? ((Number) object).longValue() : 0;
	}

	/**
	 * get the represented {@link Object}
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * get the represented object as a type
	 * 
	 * @param cls
	 *            the type to end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObjectAs(Class<T> cls) {
		return (T) object;
	}

	/**
	 * get the represented reflected {@link Class} of this object
	 */
	public ReflectedClass getReflectedClass() {
		return new ReflectedClass(getType());
	}

	/**
	 * get this object as a short, return 0 if null or not a Number
	 */
	public short getShort() {
		return object == null ? 0 : object instanceof Number ? ((Number) object).shortValue() : 0;
	}

	/**
	 * get the represented {@link Class} of this object
	 */
	public Class<?> getType() {
		return object == null ? type : object.getClass();
	}

	/**
	 * set a value of a declared field of this object
	 */
	public void setDeclaredField(String name, Object value) {
		Class<?> cls = getType();
		Exception e = null;
		do {
			try {
				Field field = cls.getDeclaredField(name);
				field.setAccessible(true);
				field.set(object, value);
			} catch (Exception e1) {
				if (e == null)
					e = e1;
			}
			cls = cls.getSuperclass();
		} while (cls != Object.class);
		e.printStackTrace();
	}

	/**
	 * set a value of a field of this object
	 */
	public void setField(String name, Object value) {
		try {
			Field field = type.getField(name);
			field.setAccessible(true);
			field.set(object, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the object represented by this {@link ReflectedObject}
	 * 
	 * @deprecated create a new {@link ReflectedObject} instead
	 */
	public void setObject(Object object) {
		this.object = object;
		this.type = getType();
	}

	@Override
	public String toString() {
		return type.getName() + " : " + object;
	}
}
