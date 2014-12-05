package toni.druck.filter;

import toni.druck.core2.DataItem;

public class AppendFilter extends BasisFilter {
	private boolean pre;
	private String command;
	private String[] append;

	public void setPre(boolean pre) {
		this.pre = pre;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setAppend(String append) {
		this.append = append.split(" *, *");
	}

	public AppendFilter() {
	}

	@Override
	public void receive(DataItem item) {
		if (getCommand().equals(item.getCommand())) {
			if (pre) {
				append(item);
				send(item);
			} else {
				send(item);
				append(item);
			}

		} else {
			send(item);
		}
	}

	private void append(DataItem item) {
		for (String command : append) {
			DataItem appendIt = new DataItem(command, item);
			send(appendIt);
		}
	}

}
