package tester.filter;

import java.util.Vector;

import toni.druck.page.DataItem;

public class TestDataItem extends DataItem {

	Vector<String> filterReihe = new Vector<String>();

	
	public TestDataItem(String text) {
			super(text);
	}

	public Vector<String> getFilterReihe() {
		return filterReihe;
	}

	public void addName(String name) {
		filterReihe.add(name);
	}

}
