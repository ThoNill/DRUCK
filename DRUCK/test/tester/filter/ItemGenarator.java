package tester.filter;

import java.util.Vector;

import toni.druck.core2.DataItem;
import toni.druck.filter.Filter;

public class ItemGenarator {
	Vector<String> commands = new Vector<String>();

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
