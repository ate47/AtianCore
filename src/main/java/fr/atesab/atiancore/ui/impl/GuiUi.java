package fr.atesab.atiancore.ui.impl;

import fr.atesab.atiancore.ui.Ui;
import net.minecraft.client.gui.GuiScreen;

public class GuiUi extends GuiScreen {
	private String name;
	private Ui ui;

	public GuiUi(String name, Ui ui) {
		this.name = name;
		this.ui = ui;
	}
	@Override
	public void onGuiClosed() {
		ui.onGuiClosed();
		super.onGuiClosed();
	}
	@Override
	public boolean allowCloseWithEscape() {
		return ui.allowCloseWithEscape();
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return ui.charTyped(key, modifier) || super.charTyped(key, modifier);
	}

	public String getName() {
		return name;
	}

	@Override
	protected void initGui() {
		ui.setupAndReset();
		super.initGui();
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		return ui.keyPressed(keyCode, scan, modifier) || super.keyPressed(keyCode, scan, modifier);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		ui.render(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void tick() {
		ui.tick();
		super.tick();
	}

	public void setName(String name) {
		this.name = name;
	}
}
