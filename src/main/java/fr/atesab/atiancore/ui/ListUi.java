package fr.atesab.atiancore.ui;

import java.util.ArrayList;
import java.util.List;

import fr.atesab.atiancore.ui.element.Button;
import fr.atesab.atiancore.ui.element.TextField;

public class ListUi extends Ui {
	private List<ListElement> childrens = new ArrayList<>();
	private List<ListElement> searchElement = new ArrayList<>();
	private List<Button> buttons = new ArrayList<>();
	private TextField search;
	private Button left, right;
	private boolean init = false;
	private int firstElement, lastElement, paddingTop, paddingBottom, sizeBetweenElement = 4, cellSize;

	public ListUi(String name, Ui parent) {
		super(name, parent);
	}

	public Button addButton(String text) {
		Button b = new Button(0, 0, 0, 0, text);
		buttons.add(b);
		return b;
	}

	public void addChildren(ListElement element) {
		childrens.add(element);
		if (init)
			defineButtons();
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		for (int i = firstElement; i < lastElement; i++)
			if (searchElement.get(i).charTyped(key, modifier))
				return true;
		boolean out = super.charTyped(key, modifier);

		if (search.isFocused()) {
			search();
		}

		return out;
	}

	private void search() {
		searchElement.clear();
		String search = this.search.getText().toLowerCase();
		childrens.stream().filter(e -> e.match(search)).forEach(searchElement::add);
		firstElement = 0;

		defineButtons();
	}

	private void defineButtons() {
		cellSize = childrens.stream().mapToInt(ListElement::getWidth).max().orElse(0);

		int pageHeight = getHeight() - (paddingBottom + paddingTop);
		int currentSize = 0;
		int cells = getWidth() / (sizeBetweenElement + cellSize);
		int cell = 0;
		for (lastElement = firstElement; lastElement < searchElement.size() && cell < cells;) {
			ListElement e = searchElement.get(lastElement);
			int sizeY = e.getHeight() + sizeBetweenElement;
			if (currentSize + sizeY > pageHeight) {
				currentSize = e.getHeight();
				cell++;
			} else {
				currentSize += sizeY;
			}
			lastElement++;
		}

		int x, y;
		if (lastElement == searchElement.size()) {
			x = (getWidth() - (1 + cell) * cellSize) / 2;
			y = cell == 0 ? (getHeight() - searchElement.stream().mapToInt(ListElement::getHeight).sum()) : paddingTop;
		} else {
			x = (getWidth() - (cells) * cellSize) / 2;
			y = paddingTop;
		}
		
		for (int i = firstElement; i < lastElement; i++) {
			ListElement e = searchElement.get(i);
			e.relocate(x, y);
			int sizeY = e.getHeight() + sizeBetweenElement;

			if (y + sizeY > pageHeight) {
				y = paddingTop + e.getHeight();
				x += cellSize;
			} else
				y += sizeY;
		}

		left.setEnabled(firstElement > 0);
		right.setEnabled(lastElement < searchElement.size());

		init = true;
	}

	public int getSizeBetweenElement() {
		return sizeBetweenElement;
	}

	public void nextPage() {
		firstElement = lastElement;
		int pageHeight = getHeight() - (paddingBottom + paddingTop);
		int currentSize = 0;
		int cells = getWidth() / (sizeBetweenElement + cellSize);
		int cell = 0;
		for (; lastElement < searchElement.size() && cell < cells;) {
			ListElement e = searchElement.get(lastElement);
			int sizeY = e.getHeight() + sizeBetweenElement;
			if (currentSize + sizeY > pageHeight) {
				currentSize = e.getHeight();
				cell++;
			} else {
				currentSize += sizeY;
			}
			lastElement++;
		}
		defineButtons();
	}

	public void lastPage() {
		lastElement = firstElement;
		int pageHeight = getHeight() - (paddingBottom + paddingTop);
		int currentSize = 0;
		int cells = getWidth() / (sizeBetweenElement + cellSize);
		int cell = cells;
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
	public void init() {
		int tfSize = getWidth() * 2 / 3;
		String search = this.search != null ? this.search.getText() : "";
		this.search = addTextField(font, (getWidth() - tfSize) / 2, font.getHeigth() + 4, tfSize, 20);
		paddingTop = this.search.getX() + this.search.getHeight() + 4;
		this.search.setMaxLength(Integer.MAX_VALUE);
		this.search.setText(search);

		left = addButton(getWidth() / 2 - 200, getHeight() - 24, 20, 20, "<-").action(b -> lastPage());
		right = addButton(getWidth() / 2 + 180, getHeight() - 24, 20, 20, "->").action(b -> nextPage());

		paddingBottom = 24 + 4;

		search();
		super.init();
	}

	@Override
	public boolean keyPressed(int keyCode, int scan, int modifier) {
		for (int i = firstElement; i < lastElement; i++)
			if (searchElement.get(i).keyPressed(keyCode, scan, modifier))
				return true;
		boolean out = super.keyPressed(keyCode, scan, modifier);

		if (search.isFocused()) {
			search();
		}

		return out;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawBackground();
		font.drawCenterString(getName(), getWidth() / 2, 2, 0xffff77);
		for (ListElement e : childrens)
			e.render(mouseX, mouseY, partialTicks);
		for (int i = firstElement; i < lastElement; i++)
			searchElement.get(i).render(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
	}

	public void setSizeBetweenElement(int sizeBetweenElement) {
		this.sizeBetweenElement = sizeBetweenElement;
		if (init)
			defineButtons();
	}

	@Override
	public void tick() {
		for (int i = firstElement; i < lastElement; i++)
			searchElement.get(i).tick();
		super.tick();
	}

}
