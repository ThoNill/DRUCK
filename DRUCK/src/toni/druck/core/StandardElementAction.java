package toni.druck.core;

import toni.druck.core2.DataModel;
import toni.druck.core2.Element;
import toni.druck.core2.Page;

public abstract class StandardElementAction implements ElementAction {
	
	public void prepareForPrint(Element elem) {
		prepareForPrint(elem,elem.getData(),elem.getPage());
	}

	abstract protected void prepareForPrint(Element elem, DataModel data, Page page);


}
