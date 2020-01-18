package fr.atesab.atiancore.ui.core;

import fr.atesab.atiancore.AtianCore;
import fr.atesab.atiancore.ui.Ui;
import fr.atesab.atiancore.ui.element.BooleanButton;
import fr.atesab.atiancore.ui.element.Button;
import net.minecraft.client.resources.I18n;

public class CoreConfigUi extends Ui {
	private AtianCore core;
	private boolean oldDebug;

	public CoreConfigUi(Ui parent, AtianCore core) {
		super(I18n.format("atiancore.gui.config"), parent);
		this.core = core;
		oldDebug = core.isDebugMode();
	}

	@Override
	public void init() {
		int i = -1;
		Button commands = addButton(getWidth() / 2 - 100, getHeight() / 2 + 24 * i++, 200, 20,
				I18n.format("atiancore.gui.commandList")).addAction(b -> new CommandListUi(this).display());
		
		commands.setEnabled(core.isDebugMode());
		
		addChildren(new BooleanButton(getWidth() / 2 - 100, getHeight() / 2 + 24 * i++, 200, 20,
				I18n.format("atiancore.gui.debug"), core::setDebugMode, core::isDebugMode)).addAction(b -> {
					commands.setEnabled(core.isDebugMode());
				});
		addButton(getWidth() / 2 - 100, getHeight() / 2 + 24 * i++, 200, 20, I18n.format("gui.done"))
				.addAction(b -> displayParent());
		super.init();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawBackground();
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		if (oldDebug != core.isDebugMode())
			core.saveConfig();
		super.onGuiClosed();
	}
}
