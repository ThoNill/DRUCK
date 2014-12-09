package toni.druck.standardElemente;

import toni.druck.elements.Hbox;
import toni.druck.page.Element;
import toni.druck.page.Page;


/*****
 * 
 * @author Thomas Nill
 * 
 * Basisklasse für Horizontale und Vertikale Boxen {@link Hbox}, {@Link Vbox}
 * 
 * 
 */
public class VHElement extends StandardElement {

	private int mheight = 0;
	private int mwidth = 0;

	public VHElement(String name, Page page) {
		super(name, page);
	}

	public VHElement() {
		super();
	}

	@Override
	public void setHeight(int h) {
		mheight = h;
		super.setHeight(h);
	}

	@Override
	public void setWidth(int w) {
		mwidth = w;
		super.setWidth(w);
	}

	protected void setzeWidthAndHeight(int h, int w) {
		if (mwidth == 0 || mwidth < w) {
			super.setWidth(w);
		}
		if (mheight == 0 || mheight < w) {
			super.setHeight(h);
		}
	}
}
