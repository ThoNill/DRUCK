package toni.druck.elements;

import toni.druck.elementRenderer.ElementRenderer;
import toni.druck.model.DataModel;
import toni.druck.page.Page;
import toni.druck.standardElemente.Variable;

public class TextField extends Variable {

	public TextField(String name, Page page) {
		super(name, page);
	}

	public TextField() {
		super();
	}

	@Override
	public String[] getVariablen() {
		ElementRenderer renderer = getRenderer();
		String s[] = new String[felder.length];
		DataModel m = getPage().getData();
		for (int i = 0; i < s.length; i++) {
			s[i] = m.get(felder[i]);
			if (renderer != null) {
				s[i] = renderer.render(s[i]);
			}
		}
		return s;
	}

}
