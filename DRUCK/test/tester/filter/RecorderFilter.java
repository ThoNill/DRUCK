package tester.filter;

import java.util.Vector;

import toni.druck.core2.DataItem;
import toni.druck.filter.BasisFilter;
import toni.druck.filter.EmptyFilter;

public class RecorderFilter extends BasisFilter {
	private Vector<String> commands = new Vector<String>();

	public Vector<String> getCommands() {
		return commands;
	}

	public RecorderFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receive(DataItem item) {
		commands.add(item.getCommand());
		send(item);
	}

}
