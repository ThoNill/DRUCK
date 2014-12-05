package toni.druck.elementActions;

import toni.druck.core.StandardElement;
import toni.druck.core.StandardElementAction;
import toni.druck.core2.DataModel;
import toni.druck.core2.Element;
import toni.druck.core2.Page;

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