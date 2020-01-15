package fr.atesab.atiancore.ui.element;

import fr.atesab.atiancore.ui.Font;
import net.minecraft.client.gui.GuiTextField;

public class TextField implements Element {
	private GuiTextField mcTextField;

	public TextField(Font font, int x, int y, int width, int height) {
		mcTextField = new GuiTextField(0, font.getRenderer(), x, y, width, height);
	}

	@Override
	public int getHeight() {
		return mcTextField.height;
	}

	public int getMaxLength() {
		return mcTextField.getMaxStringLength();
	}

	@Deprecated
	public GuiTextField getMcTextField() {
		return mcTextField;
	}

	@Override
	public String getText() {
		return mcTextField.getText();
	}

	@Override
	public int getWidth() {
		return mcTextField.width;
	}

	@Override
	public int getX() {
		return mcTextField.x;
	}

	@Override
	public int getY() {
		return mcTextField.y;
	}

	@Override
	public boolean isVisible() {
		return mcTextField.getVisible();
	}

	public void setEnabled(boolean value) {
		mcTextField.setEnabled(value);
	}

	public void setForegroundColor(int color) {
		mcTextField.setTextColor(color);
	}

	@Override
	public void setHeight(int value) {
		mcTextField.height = value;
	}

	public void setMaxLength(int length) {
		mcTextField.setMaxStringLength(length);
	}

	@Override
	public void setText(String text) {
		mcTextField.setText(text);
	}

	@Override
	public void setVisible(boolean value) {
		mcTextField.setVisible(value);
	}

	@Override
	public void setWidth(int value) {
		mcTextField.width = value;
	}

	@Override
	public void setX(int value) {
		mcTextField.x = value;
	}

	@Override
	public void setY(int value) {
		mcTextField.y = value;
	}

	@Override
	public void tick() {
		mcTextField.tick();
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return getMcTextField().charTyped(key, modifier);
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		return getMcTextField().keyPressed(keyCode, scan, modifier);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		getMcTextField().drawTextField(mouseX, mouseY, partialTicks);
	}

	public boolean isFocused() {
		return getMcTextField().isFocused();
	}
}
