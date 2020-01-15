package fr.atesab.atiancore.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import fr.atesab.atiancore.ui.element.Button;
import fr.atesab.atiancore.ui.element.Element;
import fr.atesab.atiancore.ui.element.Slider;
import fr.atesab.atiancore.ui.element.TextField;
import fr.atesab.atiancore.ui.impl.GuiUi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/**
 * an abstract view of a game ui
 * 
 * @author ATE47
 *
 */
public class Ui {
	@FunctionalInterface
	public interface BuildUiConsumer<UI extends Ui> {
		UI build(Ui parent);
	}

	/**
	 * create an abstract view from a real game screen
	 * 
	 * @param screen
	 *            the non abstract ui
	 * @return an abstract ui or null if screen is null
	 */
	public static Ui createFakeUi(@Nullable GuiScreen screen) {
		if (screen == null)
			return null;
		Ui fake = new Ui();
		fake.mcScreen = screen;
		return fake;
	}

	/**
	 * build and display a screen giving the current screen
	 * 
	 * @param builder the builder
	 * @return the ui
	 */
	public static <UI extends Ui> UI display(BuildUiConsumer<UI> builder) {
		UI ui = builder.build(createFakeUi(Minecraft.getInstance().currentScreen));
		ui.display();
		return ui;
	}

	private List<Element> childrens = new ArrayList<>();
	protected Font font = Font.NORMAL;
	private GuiScreen mcScreen;
	private Ui parentUi;

	private Ui() {
	}

	public Ui(String name, Ui parent) {
		mcScreen = new GuiUi(name, this);
		this.parentUi = parent;
	}

	/**
	 * add an abstract button to this UI, it's recommended to use this function in
	 * {@link #init()}
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param text
	 * @return the created abstract button
	 */
	protected Button addButton(int x, int y, int width, int height, String text) {
		Button b = new Button(x, y, width, height, text);
		childrens.add(b);
		return b;
	}

	/**
	 * add an abstract slider to this UI, it's recommended to use this function in
	 * {@link #init()}
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param text
	 * @param minVal
	 * @param maxVal
	 * @param currentVal
	 * @param showDec
	 *            show decimal value
	 * @param drawStr
	 *            show the text
	 * @return the created abstract slider
	 */
	protected Slider addSlider(int x, int y, int width, int height, String text, double minVal, double maxVal,
			double currentVal, boolean showDec, boolean drawStr) {
		Slider b = new Slider(x, y, width, height, text, minVal, maxVal, currentVal, showDec, drawStr);
		childrens.add(b);
		return b;
	}

	protected TextField addTextField(Font font, int x, int y, int width, int height) {
		TextField f = new TextField(font, x, y, width, height);
		childrens.add(f);
		return f;
	}

	/**
	 * Called when escape is pressed in this gui.
	 * 
	 * @return true if the GUI is allowed to close from this press.
	 */
	public boolean allowCloseWithEscape() {
		return true;
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

	/**
	 * display the main menu screen or the game
	 */
	public void close() {
		Minecraft.getInstance().displayGuiScreen((GuiScreen) null);
	}

	/**
	 * display this ui
	 */
	public void display() {
		Minecraft.getInstance().displayGuiScreen(mcScreen);
	}

	/**
	 * display the parent ui
	 */
	public void displayParent() {
		Minecraft.getInstance().displayGuiScreen(parentUi.getMcScreen());
	}

	/**
	 * draw the default background
	 */
	protected void drawBackground() {
		mcScreen.drawDefaultBackground();
	}

	/**
	 * @return a modifiable list of childrens in this ui
	 */
	public List<Element> getChildrens() {
		return childrens;
	}

	/**
	 * @return the height of the ui
	 */
	public int getHeight() {
		return mcScreen.height;
	}

	/**
	 * @return the non abstract game ui
	 */
	public GuiScreen getMcScreen() {
		return mcScreen;
	}

	/**
	 * @return the name of this ui
	 */
	public String getName() {
		return getUi().getName();
	}

	/**
	 * @return the parent non abstracted game ui
	 */
	@Nullable
	public GuiScreen getParent() {
		return parentUi == null ? null : parentUi.getMcScreen();
	}

	/**
	 * @return the parent ui
	 */
	public Ui getParentUi() {
		return parentUi;
	}

	private GuiUi getUi() {
		if (mcScreen instanceof GuiUi)
			return (GuiUi) mcScreen;
		throw new IllegalAccessError("A fake gui can't use this function");
	}

	/**
	 * @return the width of the ui
	 */
	public int getWidth() {
		return mcScreen.width;
	}

	/**
	 * called when the ui is show or when the window is resized
	 */
	public void init() {
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
	 * call when the ui is closed
	 */
	public void onGuiClosed() {
	}

	/**
	 * call when the ui need to be rendered
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param partialTicks
	 */
	public void render(int mouseX, int mouseY, float partialTicks) {
		for (Element e : childrens)
			e.render(mouseX, mouseY, partialTicks);
	}

	/**
	 * set the ui name
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		getUi().setName(name);
	}

	/**
	 * clear the childrens and init
	 */
	public void setupAndReset() {
		childrens.clear();
		init();
	}

	/**
	 * call every ui tick
	 */
	public void tick() {
		childrens.forEach(Element::tick);
	}
}
