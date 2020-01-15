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

	public Button action(Consumer<Button> action) {
		this.action = action;
		return this;
	}

	public int getForegroundColor() {
		return mcButton.packedFGColor;
	}

	@Override
	public int getHeight() {
		return mcButton.height;
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

	public boolean isHovered() {
		return mcButton.isMouseOver();
	}

	@Override
	public boolean isVisible() {
		return mcButton.visible;
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

	@Deprecated
	public GuiButton getMcButton() {
		return mcButton;
	}

	@Override
	public void tick() {
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return getMcButton().charTyped(key, modifier);
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		return getMcButton().keyPressed(keyCode, scan, modifier);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		getMcButton().render(mouseX, mouseY, partialTicks);
	}
	

}
