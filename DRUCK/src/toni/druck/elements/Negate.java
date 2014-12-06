package toni.druck.elements;

import toni.druck.model.DataModel;

public class Negate extends Add {


	public Negate(String placeNames, String sresult, DataModel m) {
		super(placeNames,sresult,m);
	}

	public Negate() {
		super();
	}


	public void perform() {
		resultValue(-add());
	}

}
