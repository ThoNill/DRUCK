package toni.druck.filter;

import toni.druck.page.DataItem;

public class EmptyFilter extends BasisFilter {

	public EmptyFilter() {
		super();
	}

	@Override
	public void receive(DataItem item) {
		send(item);
	}

}
