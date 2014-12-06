package toni.druck.elements;

import toni.druck.model.DataModel;
import toni.druck.page.Action;
import toni.druck.page.Operation;

public class Add extends Operation implements Action {

	private int place[] = null;
	private String fields;
	private String sresult;
	private int iresult;

	public Add(String placeNames, String sresult, DataModel m) {
		super();
		setFields(placeNames);
		setResult(sresult);
		setDataModel(m);
		connect(m);
	}

	public Add() {
		super();
	}

	public String getFields() {
		return fields;
	}

	public String getResult() {
		return sresult;
	}

	public void setFields(String placeNames) {
		this.fields = placeNames;
	}

	public void setResult(String sresult) {
		this.sresult = sresult;
	}

	@Override
	public void connect(DataModel source) {
		place = source.getMultiIndex(fields);
		iresult = source.getIndex(sresult);
	}

	public void perform() {
		long sum = add();
		resultValue(sum);
	}

	protected void resultValue(long sum) {
		writeMoney(iresult, sum);
	}

	protected long add() {
		long[] erg = readArguments(place);
		long sum = 0;
		for (int i = 0; i < erg.length; i++) {
			sum += erg[i];
		}
		return sum;
	}

}
