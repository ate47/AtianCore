package fr.atesab.atiancore.ui.element;

import java.util.function.Consumer;

import net.minecraft.client.gui.GuiButton;

public class Button implements Element {
	private GuiButton mcButton;
	private Consumer<Button> action;

	public Button(int x, int y, int width, int height, String text) {
		mcButton = new GuiButton(0, x, y, width, height, text) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				if (action != null)
					action.accept(Button.this);
				super.onClick(mouseX, mouseY);
			}
		};
	}

	/**
	 * add an action when the client press the button, this action will be performed
	 * after every already registered action
	 * 
	 * @param action the action to add
	 * @return this
	 */
	public Button addAction(Consumer<Button> action) {
		this.action = this.action == null ? action : this.action.andThen(action);
		return this;
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return getMcButton().charTyped(key, modifier);
	}

	public int getForegroundColor() {
		return mcButton.packedFGColor;
	}

	@Override
	public int getHeight() {
		return mcButton.height;
	}

	@Deprecated
	public GuiButton getMcButton() {
		return mcButton;
	}

	@Override
	public String getText() {
		return mcButton.displayString;
	}

	@Override
	public int getWidth() {
		return mcButton.width;
	}

	@Override
	public int getX() {
		return mcButton.x;
	}

	@Override
	public int getY() {
		return mcButton.y;
	}

	public boolean isEnabled() {
		return mcButton.enabled;
	}

	@Override
	public boolean isFocused() {
		return false;
	}

	public boolean isHovered() {
		return mcButton.isMouseOver();
	}

	@Override
	public boolean isVisible() {
		return mcButton.visible;
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		return getMcButton().keyPressed(keyCode, scan, modifier);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int button) {
		return getMcButton().mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(int mouseX, int mouseY, int button, double shiftX, double shiftY) {
		return getMcButton().mouseDragged(mouseX, mouseY, button, shiftX, shiftY);
	}

	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int button) {
		return getMcButton().mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		getMcButton().render(mouseX, mouseY, partialTicks);
	}

	public void setEnabled(boolean value) {
		mcButton.enabled = value;
	}

	public void setForegroundColor(int color) {
		mcButton.packedFGColor = color;
	}

	@Override
	public void setHeight(int value) {
		mcButton.height = value;
	}

	@Override
	public void setText(String text) {
		mcButton.displayString = text;
	}

	@Override
	public void setVisible(boolean value) {
		mcButton.visible = value;
	}

	@Override
	public void setWidth(int value) {
		mcButton.width = value;
	}

	@Override
	public void setX(int value) {
		mcButton.x = value;
	}

	@Override
	public void setY(int value) {
		mcButton.y = value;
	}

	@Override
	public void tick() {
	}
}
