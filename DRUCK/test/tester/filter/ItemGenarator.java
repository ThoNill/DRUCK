package tester.filter;

import java.util.ArrayList;
import java.util.List;

import toni.druck.filter.Filter;
import toni.druck.page.DataItem;

public class ItemGenarator {
	List<String> commands = new ArrayList<String>();

	public ItemGenarator(String... names) {
		addItems(names);
	}

	public void addItems(String... names) {
		for (String text : names) {
			commands.add(text);
		}
	}

	public void send(Filter filter) {
		for (String text : commands) {
			DataItem item = new DataItem(text);
			filter.receive(item);
		}
	}

}
