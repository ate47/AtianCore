package fr.atesab.atiancore.ui.core;

import java.util.Map;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;

import fr.atesab.atiancore.AtianCore;
import fr.atesab.atiancore.ui.ListElement;
import fr.atesab.atiancore.ui.ListUi;
import fr.atesab.atiancore.ui.Ui;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandSource;

public class CommandListUi extends ListUi {
	private class TextListElement extends ListElement {
		private String line;

		public TextListElement(String line) {
			super(-1, font.getHeigth());
			this.line = line;
		}

		@Override
		public void render(int mouseX, int mouseY, float partialTicks) {
			font.drawCenterString(line, CommandListUi.this.getWidth() / 2, getShiftY(), color);
			super.render(mouseX, mouseY, partialTicks);
		}

		@Override
		public boolean match(String search) {
			return line.toLowerCase().contains(search);
		}
	}

	private int color;

	public CommandListUi(Ui parent) {
		super(I18n.format("atiancore.gui.commandList"), parent);
		withDoneButton().withOneCellPerList(true).withSizeBetweenElement(0);
		CommandDispatcher<CommandSource> dispatcher = AtianCore.getCommandDispatcher();
		try {
			Map<CommandNode<CommandSource>, String> usages = dispatcher.getSmartUsage(dispatcher.getRoot(), null);
			if (usages.isEmpty()) {
				color = 0xffffffff;
				addListElement(new TextListElement("(" + I18n.format("atiancore.gui.empty") + ")"));
			} else {
				color = 0xff9999ff;
				usages.values().forEach(line -> addListElement(new TextListElement('/' + line)));
			}
		} catch (Exception e) {
			getListElements().clear();
			e.printStackTrace();
			color = 0xffff4444;
			addListElement(new TextListElement(e.getClass().getName() + ": " + e.getMessage()));
			StackTraceElement[] trace = e.getStackTrace();
			int n = Math.min(trace.length, 15);
			for (int j = 0; j < n; ++j)
				addListElement(new TextListElement("at " + trace[j].getMethodName() + " (" + trace[j].getFileName()
						+ ":" + (trace[j].getLineNumber() < 0 ? "???" : trace[j].getLineNumber()) + ")"));
		}
	}

}
