package toni.druck.elements;

import toni.druck.core.Variable;

public class Haken extends Variable {

	private boolean vh = true;

	public Haken() {
		super();
	}

	public boolean isVh() {
		return vh;
	}

	public void setVh(boolean vh) {
		this.vh = vh;
	}

}
