package tester.filter;

import java.util.ArrayList;
import java.util.List;

import toni.druck.filter.BasisFilter;
import toni.druck.page.DataItem;

public class RecorderFilter extends BasisFilter {
	private List<String> commands = new ArrayList<String>();

	public List<String> getCommands() {
		return commands;
	}

	public RecorderFilter() {
	}

	@Override
	public void receive(DataItem item) {
		commands.add(item.getCommand());
		send(item);
	}

}
