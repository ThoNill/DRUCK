package toni.druck.elements;

import toni.druck.standardElemente.Variable;

public class OmrStriche extends Variable {

	public OmrStriche() {
		super();
	}

	@Override
	public String getText() {
		int c = (int) Math.round(Math.random() * 16);
		String erg = " 1 ";
		int p = 2;
		for (int i = 0; i < 4; i++) {
			if (c % 2 == 1) {
				erg += " " + p + " ";
			}
			p++;
			c = c / 2;
		}
		return erg;
	}
}
