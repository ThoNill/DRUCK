package tester.help;

import toni.druck.model.DataModel;
import toni.druck.page.Element;
import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;
import toni.druck.standardElemente.StandardElementAction;

public class WieDerTextAction extends StandardElementAction {

	@Override
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
