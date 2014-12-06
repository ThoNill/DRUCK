package toni.druck.elementActions;

import toni.druck.model.DataModel;
import toni.druck.page.Element;
import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;
import toni.druck.standardElemente.StandardElementAction;

public abstract class OnOffAction extends StandardElementAction {

	protected String variable = null;
	private boolean enable = false;
	private int variableIndex = -1;

	public OnOffAction() {
		super();
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	private boolean isOn(DataModel model, Page page) {
		if (variableIndex<0) {
			variableIndex = model.getIndex(variable);
		}
		String value = page.getData().get(variableIndex);
		return isOn(value);
	}

	protected boolean isOn(String value) {
		return "true".equals(value) || "TRUE".equals(value) || "0".equals(value);
	}

	@Override
	public void prepareForPrint(Element elem, DataModel model, Page page) {
		StandardElement selem = (StandardElement)elem;
		boolean on = isOn(model, page);
		if (enable) {
			selem.setEnabled(on);
		} else {
			selem.setPrintable(on);
		}
	}

}