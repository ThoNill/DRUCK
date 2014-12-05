package toni.druck.xml;

import org.jdom2.Document;
import org.jdom2.Element;

public abstract class TreeWalker {

	public void walkAlong(Document doc) {
		Element root = doc.getRootElement();
		walkAlong(root);
	}

	protected void walkAlong(Element elem) {
		if (elem.getChildren().size() == 0)
			return;

		goUp(elem);
		for (Object e : elem.getChildren()) {
			if (e instanceof Element) {
				bearbeite((Element) e);
				walkAlong((Element) e);
			}
		}
		goDown(elem);

	}

	protected void goDown(Element elem) {
		// TODO Auto-generated method stub

	}

	protected void goUp(Element elem) {
		// TODO Auto-generated method stub

	}

	abstract protected void bearbeite(Element e);
}
