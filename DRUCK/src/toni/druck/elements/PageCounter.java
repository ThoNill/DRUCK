package toni.druck.elements;

import toni.druck.page.Page;
import toni.druck.page.PrintEvent;

public class PageCounter extends Counter {

	public PageCounter() {
		super();
	}

	@Override
	public void listenTo(PrintEvent ev) {
		if (ev.type == PrintEvent.NEUE_SEITE) {
			inc();
		}
	}

	@Override
	public void setPage(Page page) {
		super.setPage(page);
		page.addListener(this);
	}

	@Override
	public void perform() {
		set0();
	}

}
