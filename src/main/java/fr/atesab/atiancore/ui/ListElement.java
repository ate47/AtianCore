package fr.atesab.atiancore.ui;

import java.util.ArrayList;
import java.util.List;

import fr.atesab.atiancore.ui.element.Button;
import fr.atesab.atiancore.ui.element.Element;
import fr.atesab.atiancore.ui.element.Slider;
import fr.atesab.atiancore.ui.element.TextField;

public class ListElement {
	private List<Button> buttons = new ArrayList<>();
	private List<Element> childrens = new ArrayList<>();
	private List<TextField> fields = new ArrayList<>();
	private int shiftX, shiftY;
	private List<Slider> sliders = new ArrayList<>();
	private int width, height;

	public ListElement(int width, int height) {
		this.width = width;
		this.height = height;
	}

	protected Button addButton(int x, int y, int width, int height, String text) {
		Button b = new Button(x, y, width, height, text);
		buttons.add(b);
		return addChildren(b);
	}

	protected <T extends Element> T addChildren(T children) {
		childrens.add(children);
		return children;
	}

	protected Slider addSlider(int x, int y, int width, int height, String text, double minVal, double maxVal,
			double currentVal, boolean showDec, boolean drawStr) {
		Slider b = new Slider(x, y, width, height, text, minVal, maxVal, currentVal, showDec, drawStr);
		sliders.add(b);
		return addChildren(b);
	}

	protected TextField addTextField(Font font, int x, int y, int width, int height) {
		TextField f = new TextField(font, x, y, width, height);
		fields.add(f);
		return addChildren(f);
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
	 * @param search
	 *            the search query
	 * @return true if the query match this element, false otherwise
	 */
	public boolean match(String search) {
		return false;
	}

	/**
	 * call every ui tick
	 */
	public void tick() {
		childrens.forEach(Element::tick);
	}
}
