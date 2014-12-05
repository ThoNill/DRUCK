package toni.druck.elements;

import toni.druck.core.VHElement;
import toni.druck.core2.Element;
import toni.druck.core2.Page;

public class Hbox extends VHElement {

	public Hbox(String name, Page page) {
		super(name, page);
	}

	public Hbox() {
		super();
	}

	public void berechneGroesse() {
		int h = 0;
		int w = 0;
		if (childs != null) {
			for (Element e : childs) {
				e.berechneGroesse();
				h = Math.max(h, e.getHeight());
				w += e.getWidth();
			}
		}
		setzeWidthAndHeight(h, w);
	}

	public void setzePositionen() {
		int w = 0;
		if (childs != null) {
			for (Element e : childs) {
				e.setzePositionen();
				e.setRelX(w);
				e.setRelY(0);
				w += e.getWidth();
			}
		}
	}

}
