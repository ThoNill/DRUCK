package toni.druck.elements;

import toni.druck.page.Element;
import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;

public class PositionBox extends StandardElement {
	private boolean calculateWidth = true;
	private boolean calculateHeight = true;

	public PositionBox(String name, Page page) {
		super(name, page);
	}

	public PositionBox() {
		super();
	}

	public void setHeight(int height) {
		calculateHeight = false;
		super.setHeight(height);
	}

	public void setWidth(int width) {
		calculateWidth = false;
		super.setWidth(width);
	}

	public void berechneGroesse() {
		int h = 0;
		int w = 0;
		if (childs != null) {
			for (Element e : childs) {
				e.berechneGroesse();
				h = Math.max(h, e.getHeight() + e.getRelY());
				w = Math.max(w, e.getWidth() + e.getRelX());
			}
		}
		if (calculateWidth) {
			super.setWidth(w);
		}
		if (calculateHeight) {
			super.setHeight(h);
		}
	}

}
