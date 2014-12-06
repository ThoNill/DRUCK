package toni.druck.filter;

import java.util.Vector;

import toni.druck.page.DataItem;

public class IfExistsFilter extends BasisFilter {
	private boolean exclude = false;

	private String command;
	private String[] exists;
	private Vector<DataItem> betweenItems = new Vector<DataItem>();
	private Vector<DataItem> preItems = new Vector<DataItem>();

	enum Status {
		PRE_COMMAND, COMMAND, FOUND
	};

	private Status status = Status.PRE_COMMAND;

	public IfExistsFilter() {
		super();
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}

	public void setExists(String append) {
		this.exists = append.split(" *, *");
	}

	@Override
	public void receive(DataItem item) {
		String itemCommand = item.getCommand();
		boolean commandFound = (getCommand().equals(itemCommand))
				|| itemCommand.equals(DataItem.ENDOFFILE);
		if (exclude) {
			status = nextStatusExclude(status, commandFound, item);
		} else {
			status = nextStatusInclude(status, commandFound, item);
		}
		if (itemCommand.equals(DataItem.ENDOFFILE)) {
			preItems.clear();
			betweenItems.clear();
			send(item);
		}
	}

	private Status nextStatusInclude(Status status, boolean commandFound,
			DataItem item) {
		switch (status) {
		case PRE_COMMAND:
			if (commandFound) {
				betweenItems.add(item);
				return Status.COMMAND;
			} else {
				preItems.add(item);
			}
			break;
		case COMMAND:
			if (commandFound) {
				betweenItems.clear();
			}
			betweenItems.add(item);
			if (!commandFound) {
				boolean existsFound = getFound(item.getCommand());
				if (existsFound) {
					return Status.FOUND;
				}
			}
			break;
		case FOUND:
			if (commandFound) {
				send(preItems);
				send(betweenItems);
				betweenItems.add(item);
				return Status.COMMAND;
			} else {
				betweenItems.add(item);
			}
			break;
		default:
			throw new RuntimeException("nicht erlaubter Status");

		}

		return status;
	}

	private Status nextStatusExclude(Status status, boolean commandFound,
			DataItem item) {
		switch (status) {
		case PRE_COMMAND:
			if (commandFound) {
				betweenItems.add(item);
				return Status.FOUND;
			} else {
				preItems.add(item);
			}
			break;
		case FOUND:
			if (commandFound) {
				send(preItems);
				send(betweenItems);
			}
			betweenItems.add(item);
			if (!commandFound) {
				boolean existsFound = getFound(item.getCommand());
				if (existsFound) {
					return Status.COMMAND;
				}
			}
			break;
		case COMMAND:
			if (commandFound) {
				betweenItems.clear();
				betweenItems.add(item);
				return Status.FOUND;
			}
			break;
		default:
			throw new RuntimeException("nicht erlaubter Status");

		}

		return status;
	}

	private boolean getFound(String command) {
		for (String e : exists) {
			if (command.equals(e)) {
				return true;
			}
		}
		return false;
	}

	private void send(Vector<DataItem> items) {
		for (DataItem item : items) {
			send(item);
		}
		items.clear();
	}

}
