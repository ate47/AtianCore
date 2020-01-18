package fr.atesab.atiancore.ui.element;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * an abstract {@link Button} that switch between values
 * 
 * @author ATE47
 *
 * @param <T>
 *            the value type
 */
public class EnumButton<T> extends ValueButton<T> {
	private class EnumButtonConsumer implements Consumer<Button> {
		private int index;
		private T[] elements;

		public EnumButtonConsumer(T[] elements, T element) {
			index = Arrays.asList(elements).indexOf(element);
		}

		@Override
		public void accept(Button t) {
			index = (index + 1) % elements.length;
			setValue(elements[index]);
			updateDisplay();
		}
	}

	public EnumButton(int x, int y, int width, int height, String text, T[] elements, Consumer<T> setter,
			Supplier<T> getter) {
		super(x, y, width, height, text, setter, getter);
		addAction(new EnumButtonConsumer(elements, getter.get()));
	}

	public EnumButton(int x, int y, int width, int height, String text, T[] elements, T element) {
		super(x, y, width, height, text, element);
		addAction(new EnumButtonConsumer(elements, element));
	}

}
