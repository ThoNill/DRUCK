package toni.druck.core2;

public class VerteilerConnector {
	String name;
	PrintListener listener;

	public VerteilerConnector(String name, PrintListener listener) {
		super();
		this.name = name;
		this.listener = listener;
	}

	public String getName() {
		return name;
	}

	public PrintListener getListener() {
		return listener;
	}
}
