package tester.filter;

import toni.druck.filter.EmptyFilter;
import toni.druck.page.DataItem;

public class TestFilter extends EmptyFilter {
	public String name;
	private int count;
	
	public int getCount() {
		return count;
	}

	public TestFilter(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void receive(DataItem item) {
		count++;
		if (item instanceof TestDataItem) {
			((TestDataItem)item).addName(name);
		}
		super.receive(item);
	}
	

}
