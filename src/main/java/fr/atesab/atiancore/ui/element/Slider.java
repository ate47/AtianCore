package fr.atesab.atiancore.ui.element;

import java.util.function.Consumer;

import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;

public class Slider implements Element, ISlider {
	private GuiSlider mcSlider;
	private Consumer<Slider> action;

	public Slider(int x, int y, int width, int height, String text, double minVal, double maxVal, double currentVal,
			boolean showDec, boolean drawStr) {
		this.mcSlider = new GuiSlider(0, x, y, width, height, "", "", minVal, maxVal, currentVal, showDec, drawStr,
				this);
	}

	public Slider action(Consumer<Slider> action) {
		this.action = action;
		return this;
	}

	@Override
	public int getHeight() {
		return mcSlider.height;
	}

	public double getMaxValue() {
		return mcSlider.maxValue;
	}

	@Deprecated
	public GuiSlider getMcSlider() {
		return mcSlider;
	}

	public double getMinValue() {
		return mcSlider.minValue;
	}

	@Override
	public String getText() {
		return mcSlider.displayString;
	}

	public double getValue() {
		return mcSlider.sliderValue;
	}

	@Override
	public int getWidth() {
		return mcSlider.width;
	}

	@Override
	public int getX() {
		return mcSlider.x;
	}

	@Override
	public int getY() {
		return mcSlider.y;
	}

	@Override
	public boolean isVisible() {
		return mcSlider.visible;
	}

	@Override
	public void onChangeSliderValue(GuiSlider slider) {
		if (action != null)
			action.accept(this);
	}

	@Override
	public void setHeight(int value) {
		mcSlider.height = value;
	}

	public void setMaxValue(double value) {
		mcSlider.maxValue = value;
	}

	public void setMinValue(double value) {
		mcSlider.minValue = value;
	}

	@Override
	public void setText(String text) {
		mcSlider.displayString = text;
	}

	public void setValue(double value) {
		mcSlider.sliderValue = value;
	}

	@Override
	public void setVisible(boolean value) {
		mcSlider.visible = value;
	}

	@Override
	public void setWidth(int value) {
		mcSlider.width = value;
	}

	@Override
	public void setX(int value) {
		mcSlider.x = value;
	}

	@Override
	public void setY(int value) {
		mcSlider.y = value;
	}

	@Override
	public void tick() {
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return getMcSlider().charTyped(key, modifier);
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		return getMcSlider().keyPressed(keyCode, scan, modifier);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		getMcSlider().render(mouseX, mouseY, partialTicks);
	}
}
