package fr.atesab.atiancore.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class Font {
	public static final Font NORMAL = new Font() {
		@Override
		public FontRenderer getRenderer() {
			return Minecraft.getInstance().fontRenderer;
		}
	};
	public static final Font GALACTIC = new Font() {
		private FontRenderer font;

		@Override
		public FontRenderer getRenderer() {
			return font == null
					? font = Minecraft.getInstance().getFontResourceManager()
							.getFontRenderer(Minecraft.standardGalacticFontRenderer)
					: font;
		}
	};

	public void drawCenterString(String text, int x, int y) {
		drawString(text, x - getStringWidth(text) / 2, y);
	}

	public void drawCenterString(String text, int x, int y, int color) {
		drawString(text, x - getStringWidth(text) / 2, y, color);
	}

	public void drawRight(String text, int x, int y) {
		drawString(text, x - getStringWidth(text), y);
	}

	public void drawRightString(String text, int x, int y, int color) {
		drawString(text, x - getStringWidth(text), y, color);
	}

	public void drawString(String text, int x, int y) {
		drawString(text, x, y, 0xffffffff);
	}

	public void drawString(String text, int x, int y, int color) {
		getRenderer().drawString(text, x, y, color);
	}

	public int getHeigth() {
		return getRenderer().FONT_HEIGHT;
	}

	public abstract FontRenderer getRenderer();

	public int getStringWidth(String text) {
		return getRenderer().getStringWidth(text);
	}
}
