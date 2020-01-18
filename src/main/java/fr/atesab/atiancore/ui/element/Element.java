package fr.atesab.atiancore.ui.element;

import fr.atesab.atiancore.ui.Ui;

/**
 * an abstract element on an {@link Ui}
 * 
 * @author ATE47
 *
 */
public interface Element {

	boolean charTyped(char key, int modifier);

	int getHeight();

	String getText();

	int getWidth();

	int getX();

	int getY();

	boolean isFocused();

	default boolean isHover(int x, int y) {
		return getX() <= x && getX() + getWidth() > x && getY() <= y && getY() + getHeight() > y;
	}

	boolean isVisible();

	boolean keyPressed(int keyCode, int scan, int modifier);

	boolean mouseClicked(int mouseX, int mouseY, int button);

	boolean mouseDragged(int mouseX, int mouseY, int button, double shiftX, double shiftY);

	boolean mouseReleased(int mouseX, int mouseY, int button);

	void render(int mouseX, int mouseY, float partialTicks);

	void setHeight(int value);

	void setText(String text);

	void setVisible(boolean value);

	void setWidth(int value);

	void setX(int value);

	void setY(int value);

	void tick();
}
