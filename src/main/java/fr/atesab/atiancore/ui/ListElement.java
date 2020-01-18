package fr.atesab.atiancore.ui;

import java.util.ArrayList;
import java.util.List;

import fr.atesab.atiancore.ui.element.Button;
import fr.atesab.atiancore.ui.element.Element;
import fr.atesab.atiancore.ui.element.Slider;
import fr.atesab.atiancore.ui.element.TextField;

public class ListElement {
	private List<Element> childrens = new ArrayList<>();
	private int shiftX, shiftY;
	private int width, height;

	/**
	 * @param width
	 *            negative for the ui width
	 * @param height
	 *            the height of the element, positive
	 */
	public ListElement(int width, int height) {
		this.width = width;
		if (height < 0)
			throw new IllegalArgumentException("negative height");
		this.height = height;
	}

	protected Button addButton(int x, int y, int width, int height, String text) {
		return addChildren(new Button(x, y, width, height, text));
	}

	protected <T extends Element> T addChildren(T children) {
		childrens.add(children);
		if (shiftX != 0)
			children.setX(children.getX() + shiftX);
		if (shiftY != 0)
			children.setY(children.getY() + shiftY);
		return children;
	}

	protected Slider addSlider(int x, int y, int width, int height, String text, double minVal, double maxVal,
			double currentVal, boolean showDec, boolean drawStr) {
		return addChildren(new Slider(x, y, width, height, text, minVal, maxVal, currentVal, showDec, drawStr));
	}

	protected TextField addTextField(Font font, int x, int y, int width, int height) {
		return addChildren(new TextField(font, x, y, width, height));
	}

	/**
	 * call when a key is typed
	 * 
	 * @param key
	 * @param modifier
	 * @return if this method consume the key
	 */
	public boolean charTyped(char key, int modifier) {
		for (Element e : childrens)
			if (e.charTyped(key, modifier))
				return true;
		return false;
	}

	public int getHeight() {
		return height;
	}

	public int getShiftX() {
		return shiftX;
	}

	public int getShiftY() {
		return shiftY;
	}

	public int getWidth() {
		return width;
	}

	public boolean isFocused() {
		for (Element e : childrens)
			if (e.isFocused())
				return true;
		return false;
	}

	/**
	 * called when a key is pressed
	 * 
	 * @param keyCode
	 * @param scan
	 * @param modifier
	 * @return if this method consume the key
	 */
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		for (Element e : childrens)
			if (e.keyPressed(keyCode, scan, modifier))
				return true;
		return false;
	}

	/**
	 * @param search
	 *            the search query
	 * @return true if the query match this element, false otherwise
	 */
	public boolean match(String search) {
		return true;
	}

	/**
	 * call when the mouse click
	 * 
	 * @param mouseX
	 *            the mouse location x
	 * @param mouseY
	 *            the mouse location y
	 * @param button
	 *            the mouse button
	 * @return if the click has been consumed
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int button) {
		for (Element e : childrens)
			if (e.mouseClicked(mouseX, mouseY, button))
				return true;
		return false;
	}

	/**
	 * call when the mouse drag a click
	 * 
	 * @param mouseX
	 *            the new mouse location x
	 * @param mouseY
	 *            the new mouse location y
	 * @param button
	 *            the mouse button
	 * @param shiftX
	 *            the location difference x
	 * @param shiftY
	 *            the location difference y
	 * @return if the drag has been consumed
	 */
	public boolean mouseDragged(int mouseX, int mouseY, int button, double shiftX, double shiftY) {
		for (Element e : childrens)
			if (e.mouseDragged(mouseX, mouseY, button, shiftX, shiftY))
				return true;
		return false;
	}

	/**
	 * call when the mouse release a click
	 * 
	 * @param mouseX
	 *            the mouse location x
	 * @param mouseY
	 *            the mouse location y
	 * @param button
	 *            the mouse button
	 * @return if the click has been consumed
	 */
	public boolean mouseReleased(int mouseX, int mouseY, int button) {
		for (Element e : childrens)
			if (e.mouseReleased(mouseX, mouseY, button))
				return true;
		return false;
	}

	public void relocate(int x, int y) {
		shift(-shiftX + x, -shiftY + y);
	}

	public void render(int mouseX, int mouseY, float partialTicks) {
		for (Element e : childrens)
			e.render(mouseX, mouseY, partialTicks);
	}

	public void shift(int dx, int dy) {
		shiftX += dx;
		shiftY += dy;
		childrens.forEach(e -> {
			e.setX(e.getX() + dx);
			e.setY(e.getY() + dy);
		});
	}

	/**
	 * call every ui tick
	 */
	public void tick() {
		childrens.forEach(Element::tick);
	}
}
