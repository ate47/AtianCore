package fr.atesab.atiancore.ui.element;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.client.resources.I18n;

/**
 * an abstract {@link Button} that switch between true and false
 * 
 * @author ATE47
 *
 */
public class BooleanButton extends EnumButton<Boolean> {
	private static final Boolean[] YES_NO = { true, false };
	private String yes, no;

	public BooleanButton(int x, int y, int width, int height, String text, boolean value) {
		super(x, y, width, height, text, YES_NO, value);
		withEnabledDisabledText();
	}

	public BooleanButton(int x, int y, int width, int height, String text, Consumer<Boolean> setter,
			Supplier<Boolean> getter) {
		super(x, y, width, height, text, YES_NO, setter, getter);
		withEnabledDisabledText();
	}
	
	@Override
	protected String getTextFromValue(Boolean value) {
		setForegroundColor(value ? 0xff77ff77 : 0xffff7777);
		return (value ? yes : no);
	}

	/**
	 * set the yes text to "enabled" and the false text to "disabled" (or translated
	 * in the current language if available)
	 * 
	 * @return this
	 */
	public BooleanButton withEnabledDisabledText() {
		withYesNoText(I18n.format("atiancore.gui.enabled"), I18n.format("atiancore.gui.disabled"));
		updateDisplay();
		return this;
	}

	/**
	 * set the yes text to "yes" and the false text to "false" (or translated in the
	 * current language if available)
	 * 
	 * @return this
	 */
	public BooleanButton withYesNoText() {
		withYesNoText(I18n.format("atiancore.gui.yes"), I18n.format("atiancore.gui.no"));
		updateDisplay();
		return this;
	}

	/**
	 * set the yes and false text of the button
	 * 
	 * @param yes
	 *            the text if the value is true
	 * @param no
	 *            the text if the value is false
	 * @return this
	 */
	public BooleanButton withYesNoText(String yes, String no) {
		this.yes = yes;
		this.no = no;
		updateDisplay();
		return this;
	}

}
