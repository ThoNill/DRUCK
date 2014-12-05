package toni.druck.elements;

import toni.druck.core2.Action;
import toni.druck.core2.DataModel;
import toni.druck.core2.Operation;

public class Clear extends Operation implements Action {

	private int place[] = null;
	private String fields;

	public Clear(String placeNames, String sresult, DataModel m) {
		super();
		setFields(placeNames);
		setDataModel(m);
		connect(m);
	}

	public Clear() {
		super();
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String placeNames) {
		this.fields = placeNames;
	}

	public void perform() {
		for (int i = 0; i < place.length; i++) {
			writeMoney(place[i], 0L);
		}
	}

	@Override
	public void connect(DataModel source) {
		place = source.getMultiIndex(fields);
	}

}