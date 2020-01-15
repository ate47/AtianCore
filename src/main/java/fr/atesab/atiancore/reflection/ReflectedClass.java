package fr.atesab.atiancore.reflection;

/**
 * A non instanced {@link ReflectedObject} with only {@link Class} information
 * 
 * @author ATE47
 *
 */
public class ReflectedClass extends ReflectedObject {
	public ReflectedClass(Class<?> type) {
		super(type);
	}

	/**
	 * create a {@link ReflectedObject} casted with this class
	 * 
	 * @param object
	 *            the object to cas
	 * @return the {@link ReflectedObject}
	 */
	public ReflectedObject cast(Object object) {
		try {
			return new ReflectedObject(getType().cast(object));
		} catch (Exception e) {
			return new ReflectedObject(object.getClass(), object);
		}
	}

	/**
	 * get a sub class declared by this class
	 * 
	 * @param index
	 *            the index of this subclass
	 * @return the reflected class
	 */
	public ReflectedClass getDeclaredClass(int index) {
		return new ReflectedClass(getType().getDeclaredClasses()[index]);
	}

	/**
	 * create a {@link ReflectedObject} from this class
	 * 
	 * @param parameters
	 *            parameters of the constructor
	 * @return the new instance
	 * @throws Exception
	 */
	public ReflectedObject getInstance(Object... parameters) throws Exception {
		return new ReflectedObject(getType().getConstructor(ReflectionUtils.getPrivimitiveParameterTypes(parameters))
				.newInstance(parameters));
	}

	/**
	 * create a {@link ReflectedObject} from this class with their types
	 * 
	 * @param parameters
	 *            parameters of the constructor
	 * @return the new instance
	 * @throws Exception
	 */
	public ReflectedObject getInstanceWithTypes(Class<?>[] parameterTypes, Object... parameters) throws Exception {
		return new ReflectedObject(getType().getConstructor(parameterTypes).newInstance(parameters));
	}
}
