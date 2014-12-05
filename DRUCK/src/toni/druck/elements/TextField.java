package toni.druck.elements;

import toni.druck.core.ElementRenderer;
import toni.druck.core.Variable;
import toni.druck.core2.DataModel;
import toni.druck.core2.Page;

public class TextField extends Variable {

	public TextField(String name, Page page) {
		super(name, page);
	}

	public TextField() {
		super();
	}

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
