package toni.druck.helper;

import org.apache.commons.beanutils.ConvertingWrapDynaBean;
import org.jdom2.Attribute;
import org.jdom2.Element;

public class SetAttributes {

	public static void setBeanAttributes(Element elem, Object reference) {
		ConvertingWrapDynaBean bean = new ConvertingWrapDynaBean(reference);

		for (Object a : elem.getAttributes()) {
			if (a instanceof Attribute) {
				bean.set(((Attribute) a).getName(), ((Attribute) a).getValue());
			}
		}
	}
}
