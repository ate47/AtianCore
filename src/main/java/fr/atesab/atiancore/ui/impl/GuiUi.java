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
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return ui.mouseClicked((int) mouseX, (int) mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double shiftX, double shiftY) {
		return ui.mouseDragged((int) mouseX, (int) mouseY, button, shiftX, shiftY)
				|| super.mouseDragged(mouseX, mouseY, button, shiftX, shiftY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return ui.mouseReleased((int) mouseX, (int) mouseY, button) || super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double factor) {
		return ui.mouseScrolled(factor) || super.mouseScrolled(factor);
	}

	@Override
	public void onGuiClosed() {
		ui.onGuiClosed();
		super.onGuiClosed();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		ui.render(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void tick() {
		ui.tick();
		super.tick();
	}
}
