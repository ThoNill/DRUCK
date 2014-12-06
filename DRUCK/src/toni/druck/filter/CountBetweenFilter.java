package toni.druck.filter;

import toni.druck.page.DataItem;

public class CountBetweenFilter extends BasisFilter {
	private String command;
	private String[] commandSplit;

	private String commandElse = null;

	private String fromElement;

	private String toElement;
	private String countElement;
	private String[] countElementSplit;

	int counter = 0;
	boolean check = false;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
		commandSplit = command.split(" *, *");
	}

	public CountBetweenFilter() {
	}

	public String getCommandElse() {
		return commandElse;
	}

	public void setCommandElse(String commandElse) {
		this.commandElse = commandElse;
	}

	@Override
	public void receive(DataItem item) {
		String c = item.getCommand();
		send(item);
		if (fromElement.equals(c)) {
			counter = 0;
			check = true;
		}
		if (check) {
			boolean increase = false;
			for (String cel : countElementSplit) {
				increase = increase || cel.equals(c);
			}
			if (increase) {
				counter++;
			}
		}
		if (toElement.equals(c)) {
			append(counter, item);
			check = false;
		}

	}

	private void append(int counter, DataItem item) {
		if (counter < commandSplit.length) {
			DataItem appendIt = new DataItem(commandSplit[counter], item);
			send(appendIt);
		} else if (commandElse != null) {
			DataItem appendIt = new DataItem(commandElse, item);
			send(appendIt);
		}
	}

	public String getFromElement() {
		return fromElement;
	}

	public void setFromElement(String fromElement) {
		this.fromElement = fromElement;
	}

	public String getToElement() {
		return toElement;
	}

	public void setToElement(String toElement) {
		this.toElement = toElement;
	}

	public String getCountElement() {
		return countElement;
	}

	public void setCountElement(String countElement) {
		this.countElement = countElement;
		countElementSplit = countElement.split(" *, *");
	}
}
