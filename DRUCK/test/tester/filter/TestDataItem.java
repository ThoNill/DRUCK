package tester.filter;

import java.util.ArrayList;
import java.util.List;

import toni.druck.page.DataItem;

public class TestDataItem extends DataItem {

	List<String> filterReihe = new ArrayList<String>();

	
	public TestDataItem(String text) {
			super(text);
	}

	public List<String> getFilterReihe() {
		return filterReihe;
	}

	public void addName(String name) {
		filterReihe.add(name);
	}

}
