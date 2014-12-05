package toni.druck.elements;

import toni.druck.core2.Page;
import toni.druck.core2.PrintEvent;

public class PageCounter extends Counter {

	public PageCounter() {
		super();
	}

	public void listenTo(PrintEvent ev) {
		if (ev.type == PrintEvent.NEUE_SEITE) {
			inc();
		}
	}

	public void setPage(Page page) {
		super.setPage(page);
		page.addListener(this);
	}

	public void perform() {
		set0();
	}

}
