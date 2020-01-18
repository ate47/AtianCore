package fr.atesab.atiancore.ui.element;

import fr.atesab.atiancore.ui.Font;
import net.minecraft.client.gui.GuiTextField;

public class TextField implements Element {
	private GuiTextField mcTextField;

	public TextField(Font font, int x, int y, int width, int height) {
		mcTextField = new GuiTextField(0, font.getRenderer(), x, y, width, height);
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return getMcTextField().charTyped(key, modifier);
	}

	@Override
	public int getHeight() {
		return getMcTextField().height;
	}

	public int getMaxLength() {
		return getMcTextField().getMaxStringLength();
	}

	@Deprecated
	public GuiTextField getMcTextField() {
		return mcTextField;
	}

	@Override
	public String getText() {
		return getMcTextField().getText();
	}

	@Override
	public int getWidth() {
		return getMcTextField().width;
	}

	@Override
	public int getX() {
		return getMcTextField().x;
	}

	@Override
	public int getY() {
		return getMcTextField().y;
	}

	@Override
	public boolean isFocused() {
		return getMcTextField().isFocused();
	}

	@Override
	public boolean isVisible() {
		return getMcTextField().getVisible();
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		return getMcTextField().keyPressed(keyCode, scan, modifier);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int button) {
		return getMcTextField().mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(int mouseX, int mouseY, int button, double shiftX, double shiftY) {
		return getMcTextField().mouseDragged(mouseX, mouseY, button, shiftX, shiftY);
	}

	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int button) {
		return getMcTextField().mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		getMcTextField().drawTextField(mouseX, mouseY, partialTicks);
	}

	public void setEnabled(boolean value) {
		getMcTextField().setEnabled(value);
	}

	public void setForegroundColor(int color) {
		getMcTextField().setTextColor(color);
	}

	@Override
	public void setHeight(int value) {
		getMcTextField().height = value;
	}

	public void setMaxLength(int length) {
		getMcTextField().setMaxStringLength(length);
	}

	@Override
	public void setText(String text) {
		getMcTextField().setText(text);
	}

	public void setFocused(boolean isFocusedIn) {
		getMcTextField().setFocused(true);
	}

	@Override
	public void setVisible(boolean value) {
		getMcTextField().setVisible(value);
	}

	@Override
	public void setWidth(int value) {
		getMcTextField().width = value;
	}

	@Override
	public void setX(int value) {
		getMcTextField().x = value;
	}

	@Override
	public void setY(int value) {
		getMcTextField().y = value;
	}

	@Override
	public void tick() {
		getMcTextField().tick();
	}
}
