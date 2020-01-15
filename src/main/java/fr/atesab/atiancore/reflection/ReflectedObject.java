package fr.atesab.atiancore.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
	 * get this object as a boolean
	 */
	public boolean getBoolean() {
		return object == null ? null : ((Boolean) object).booleanValue();
	}

	/**
	 * get this object as a byte
	 */
	public byte getByte() {
		return object == null ? null : ((Byte) object).byteValue();
	}

	/**
	 * get this object as a char
	 */
	public char getChar() {
		return object == null ? null : ((Character) object).charValue();
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
	 * get this object as a double
	 */
	public double getDouble() {
		return object == null ? null
				: object instanceof Double ? ((Double) object).doubleValue()
						: object instanceof Float ? (double) getFloat()
								: object instanceof Integer ? (double) getInteger()
										: object instanceof Short ? (double) getShort()
												: object instanceof Long ? (double) getLong()
														: ((Double) object).doubleValue();
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
	 * get this object as a float
	 */
	public float getFloat() {
		return object == null ? null
				: object instanceof Float ? ((Float) object).floatValue()
						: object instanceof Double ? (float) getDouble()
								: object instanceof Integer ? (float) getInteger()
										: object instanceof Short ? (float) getShort()
												: object instanceof Long ? (float) getLong()
														: ((Float) object).floatValue();
	}

	/**
	 * get this object as a integer
	 */
	public int getInteger() {
		return object == null ? null
				: object instanceof Integer ? ((Integer) object).intValue()
						: object instanceof Double ? (int) getDouble()
								: object instanceof Float ? (int) getFloat()
										: object instanceof Short ? (int) getShort()
												: object instanceof Long ? (int) getLong()
														: ((Integer) object).intValue();
	}

	/**
	 * get this object as a long
	 */
	public long getLong() {
		return object == null ? null
				: object instanceof Long ? ((Long) object).longValue()
						: object instanceof Double ? (long) getDouble()
								: object instanceof Float ? (long) getFloat()
										: object instanceof Short ? (long) getShort()
												: object instanceof Integer ? (long) ((Integer) object).intValue()
														: ((Long) object).longValue();
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
	 * get this object as a short
	 */
	public short getShort() {
		return object == null ? null : ((Short) object).shortValue();
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
