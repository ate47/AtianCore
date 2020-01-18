package fr.atesab.atiancore.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * a simple refrence to an object, this object can be use as a {@link Consumer}
 * and a {@link Supplier} for getter and setter
 * 
 * @author ATE47
 *
 * @param <T>
 *            the type of this object
 */
public class Reference<T> implements Consumer<T>, Supplier<T> {
	private T value;

	public Reference(T v) {
		this.value = v;
	}

	@Override
	public void accept(T value) {
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}

	/**
	 * set the stored value
	 * 
	 * @param value
	 *            the new value
	 */
	public void set(T value) {
		this.value = value;
	}
}
