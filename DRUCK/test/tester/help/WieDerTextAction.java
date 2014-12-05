package tester.help;

import toni.druck.core.ElementAction;
import toni.druck.core.StandardElement;
import toni.druck.core.StandardElementAction;
import toni.druck.core.StandardRenderer;
import toni.druck.core2.DataModel;
import toni.druck.core2.Element;
import toni.druck.core2.Page;

public class WieDerTextAction extends StandardElementAction {

	public void prepareForPrint(Element elem, DataModel model, Page page) {
		StandardElement selem = (StandardElement) elem;
		String text = selem.getText();
		selem.setEnabled(true);
		selem.setBordered(false);
		selem.setFilled(false);
		selem.setLinewidth(0);

		if (text.indexOf("bordered") >= 0) {
			selem.setBordered(true);
			selem.setLinewidth(2);
		}

		if (text.indexOf("filled") >= 0) {
			selem.setFilled(true);
			selem.setGrayscale(80);
		}

		if (text.indexOf("disabled") >= 0) {
			selem.setEnabled(false);
		}
	}
}
