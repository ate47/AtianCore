package fr.atesab.atiancore.ui;

import java.util.ArrayList;
import java.util.List;

import fr.atesab.atiancore.ui.element.Button;
import fr.atesab.atiancore.ui.element.TextField;
import net.minecraft.client.resources.I18n;

public class ListUi extends Ui {
	private List<ListElement> childrens = new ArrayList<>();
	private List<ListElement> searchElement = new ArrayList<>();
	private List<Button> buttons = new ArrayList<>();
	private TextField search;
	private Button left, right;
	private boolean init = false, oneCellList = false;
	private int firstElement, lastElement, paddingTop, paddingBottom, sizeBetweenElement = 4, cellSize;

	public ListUi(String name, Ui parent) {
		super(name, parent);
	}

	public Button addButton(String text) {
		Button b = addButton(0, 0, 0, 20, text);
		buttons.add(b);
		if (init) {
			setButtons();
		}
		return b;
	}

	public void addChildren(ListElement element) {
		childrens.add(element);
		if (init)
			defineButtons();
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		boolean focus = false;
		for (int i = firstElement; i < lastElement; i++) {
			ListElement e = searchElement.get(i);
			if (e.isFocused())
				focus = true;
			if (e.charTyped(key, modifier))
				return true;
		}
		if (!focus)
			search.setFocused(true);

		boolean out = super.charTyped(key, modifier);

		if (search.isFocused()) {
			search();
		}

		return out;
	}

	private void defineButtons() {
		cellSize = childrens.stream().mapToInt(ListElement::getWidth).max().orElse(0);

		int pageHeight = getHeight() - (paddingBottom + paddingTop);
		int currentSize = 0;
		int cells = oneCellList ? 1 : getWidth() / (sizeBetweenElement + cellSize);
		int cell = 0;
		for (lastElement = firstElement; lastElement < searchElement.size() && cell < cells;) {
			ListElement e = searchElement.get(lastElement);
			int sizeY = e.getHeight() + sizeBetweenElement;
			if (currentSize + sizeY > pageHeight) {
				currentSize = e.getHeight();
				cell++;
				if (cell == cells)
					break;
			} else {
				currentSize += sizeY;
			}
			lastElement++;
		}
		int x, y;
		if (lastElement == searchElement.size() && firstElement == 0) {
			x = (getWidth() - (1 + cell) * cellSize) / 2;
			y = cell == 0 ? (getHeight() - searchElement.stream().mapToInt(ListElement::getHeight).sum()) / 2
					: paddingTop;
		} else {
			x = (getWidth() - (cells) * cellSize) / 2;
			y = paddingTop;
		}

		for (int i = firstElement; i < lastElement; i++) {
			ListElement e = searchElement.get(i);
			int sizeY = e.getHeight() + sizeBetweenElement;
			if (y + sizeY > pageHeight + paddingTop) {
				y = paddingTop + sizeY;
				x += cellSize + sizeBetweenElement;
				e.relocate(x, paddingTop);
			} else {
				e.relocate(x, y);
				y += sizeY;
			}
		}

		left.setEnabled(firstElement > 0);
		right.setEnabled(lastElement < searchElement.size());

		init = true;
	}

	public int getSizeBetweenElement() {
		return sizeBetweenElement;
	}

	@Override
	public void init() {
		int tfSize = getWidth() * 2 / 3;
		String search = this.search != null ? this.search.getText() : "";
		this.search = addTextField(font, (getWidth() - tfSize) / 2, font.getHeigth() + 4, tfSize, 20);
		paddingTop = this.search.getY() + this.search.getHeight() + 4;
		this.search.setMaxLength(Integer.MAX_VALUE);
		this.search.setText(search);
		this.search.setFocused(true);

		left = addButton(0, getHeight() - 24, 20, 20, "<-").addAction(b -> lastPage());
		right = addButton(0, getHeight() - 24, 20, 20, "->").addAction(b -> nextPage());

		paddingBottom = 24 + 4;
		getChildrens().addAll(buttons);
		setButtons();

		search();
		super.init();
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		boolean focus = false;
		for (int i = firstElement; i < lastElement; i++) {
			ListElement e = searchElement.get(i);
			if (e.isFocused())
				focus = true;
			if (e.keyPressed(keyCode, scan, modifier))
				return true;
		}
		if (!focus)
			search.setFocused(true);

		boolean out = super.keyPressed(keyCode, scan, modifier);

		if (search.isFocused()) {
			search();
		}

		return out;
	}

	public void lastPage() {
		lastElement = firstElement;
		int pageHeight = getHeight() - (paddingBottom + paddingTop);
		int currentSize = pageHeight;
		int cells = oneCellList ? 1 : getWidth() / (sizeBetweenElement + cellSize);
		int cell = cells;

		firstElement--;

		for (; firstElement >= 0 && cell > 0;) {
			ListElement e = searchElement.get(firstElement);
			int sizeY = e.getHeight() + sizeBetweenElement;
			if (currentSize - sizeY < 0) {
				currentSize = pageHeight;
				cell--;
			} else {
				currentSize -= sizeY;
			}
			firstElement--;
		}
		if (firstElement < 0)
			firstElement = 0;
		defineButtons();
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int button) {
		if (button == 1 && search.isHover(mouseX, mouseY)) {
			search.setText("");
			search();
		} else
			for (int i = firstElement; i < lastElement; i++)
				if (searchElement.get(i).mouseClicked(mouseX, mouseY, button))
					return true;
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(int mouseX, int mouseY, int button, double shiftX, double shiftY) {
		for (int i = firstElement; i < lastElement; i++)
			if (searchElement.get(i).mouseDragged(mouseX, mouseY, button, shiftX, shiftY))
				return true;
		return super.mouseDragged(mouseX, mouseY, button, shiftX, shiftY);
	}

	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int button) {
		for (int i = firstElement; i < lastElement; i++)
			if (searchElement.get(i).mouseReleased(mouseX, mouseY, button))
				return true;
		return super.mouseReleased(mouseX, mouseY, button);
	}

	public void nextPage() {
		firstElement = lastElement;
		defineButtons();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawBackground();
		font.drawCenterString(getName(), getWidth() / 2, 2, 0xffff77);
		for (int i = firstElement; i < lastElement; i++)
			searchElement.get(i).render(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
	}

	private void search() {
		searchElement.clear();
		String search = this.search.getText().toLowerCase();
		childrens.stream().filter(e -> e.match(search)).forEach(searchElement::add);
		firstElement = 0;

		defineButtons();
	}

	private void setButtons() {
		int buttonSpace;
		int buttonWidth;
		if (buttons.size() * 202 < 352) {
			buttonSpace = buttons.size() * (buttonWidth = 200);
		} else
			buttonWidth = ((buttonSpace = 352) - buttons.size() * 2) / buttons.size();

		left.setX((getWidth() - buttonSpace) / 2 - 24);
		right.setX((getWidth() + buttonSpace) / 2 + 4);
		buttons.forEach(b -> b.setWidth(buttonWidth));
		int left = (getWidth() - buttonSpace) / 2;
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			b.setY(getHeight() - 24);
			b.setX(left + i * (buttonWidth + 2));
		}
	}

	@Override
	public void tick() {
		for (int i = firstElement; i < lastElement; i++)
			searchElement.get(i).tick();
		super.tick();
	}

	public ListUi withDoneButton() {
		addButton(I18n.format("gui.done")).addAction(b -> displayParent());
		return this;
	}

	public ListUi withOneCellPerList(boolean oneCellPerList) {
		this.oneCellList = oneCellPerList;
		return this;
	}
	
	public ListUi withSizeBetweenElement(int size) {
		if (size < 0)
			throw new IllegalArgumentException("Negative size");
		this.sizeBetweenElement = size;
		if (init)
			defineButtons();
		return this;
	}

}
