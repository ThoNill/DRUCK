package toni.druck.elementActions;

import org.apache.commons.beanutils.ConvertingWrapDynaBean;

import toni.druck.core.StandardElementAction;
import toni.druck.core2.DataModel;
import toni.druck.core2.Element;
import toni.druck.core2.Page;

public class SetAttribute extends StandardElementAction {
	private String attribute;
	private String variable;
	private int varpos = -1;
	
	public SetAttribute() {
	}
	
	public void prepareForPrint(Element elem, DataModel model, Page page) {
		ConvertingWrapDynaBean bean = new ConvertingWrapDynaBean(elem);
		if (varpos <0) {
			varpos = model.getIndex(variable);
		}
		bean.set(attribute,page.getData(varpos));
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}


}
