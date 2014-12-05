package toni.druck.xml;

import org.jdom2.Element;

public class JDOMElementWithReference extends Element {
	private Object reference;

	public Object getReference() {
		return reference;
	}

	public JDOMElementWithReference(String name, Object reference) {
		super(name);
		this.reference = reference;
	}

}
