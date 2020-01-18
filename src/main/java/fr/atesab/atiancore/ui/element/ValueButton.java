package fr.atesab.atiancore.ui.element;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.atesab.atiancore.utils.Reference;

/**
 * An abstract button that store a value
 * 
 * @author ATE47
 *
 * @param <T>
 *            value type
 */
public class ValueButton<T> extends Button {
	private Supplier<T> getter;
	private Consumer<T> setter;
	private String text;

	public ValueButton(int x, int y, int width, int height, String text, Consumer<T> setter, Supplier<T> getter) {
		super(x, y, width, height, text);
		this.getter = getter;
		this.setter = setter;
		this.text = text;
		updateDisplay();
	}

	public ValueButton(int x, int y, int width, int height, String text, T value) {
		super(x, y, width, height, text);
		Reference<T> r = new Reference<>(value);
		this.getter = r;
		this.setter = r;
		this.text = text;
		updateDisplay();
	}

	/**
	 * 
	 * @param value
	 *            the current value
	 * @return the string version of this value
	 */
	protected String getTextFromValue(T value) {
		return String.valueOf(value);
	}

	/**
	 * @return the current stored value
	 */
	public T getValue() {
		return getter.get();
	}

	/**
	 * set the stored value
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(T value) {
		setter.accept(value);
		updateDisplay();
	}

	/**
	 * update the displayed text of this button
	 */
	public void updateDisplay() {
		super.setText(text + " : " + getTextFromValue(getValue()));
	}
}
