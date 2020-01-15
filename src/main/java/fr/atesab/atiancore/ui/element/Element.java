package fr.atesab.atiancore.ui.element;

import fr.atesab.atiancore.ui.Ui;

/**
 * an abstract element on an {@link Ui}
 * 
 * @author ATE47
 *
 */
public interface Element {

	int getHeight();

	String getText();

	int getWidth();

	int getX();

	int getY();

	boolean isVisible();

	void setHeight(int value);

	void setText(String text);

	void setVisible(boolean value);

	void setWidth(int value);

	void setX(int value);

	void setY(int value);

	void tick();

	boolean charTyped(char key, int modifier);

	boolean keyPressed(int keyCode, int scan, int modifier);

	void render(int mouseX, int mouseY, float partialTicks);

}
