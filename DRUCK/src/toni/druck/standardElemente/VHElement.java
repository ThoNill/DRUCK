package toni.druck.standardElemente;

import toni.druck.page.Page;

public class VHElement extends StandardElement {

	int mheight = 0;
	int mwidth = 0;

	public VHElement(String name, Page page) {
		super(name, page);
	}

	public VHElement() {
		super();
	}

	public void setHeight(int h) {
		mheight = h;
		super.setHeight(h);
	}

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
