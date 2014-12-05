package toni.druck.filter;

import java.util.Vector;

import org.jdom2.Element;

import toni.druck.helper.ClassFactory;
import toni.druck.helper.SetAttributes;

public class FilterFactory {

	public static Filter getFilter(Element elem) {
		Filter f = (Filter) ClassFactory.getInstance(elem
				.getAttributeValue("filterClass"));
		if (f != null) {
			SetAttributes.setBeanAttributes(elem, f);
		}
		return f;
	}

	public static FilterGroup getFilterGroup(Vector<Element> elements) {
		FilterGroup group = new FilterGroup();
		for (Element elem : elements) {
			Filter filter = getFilter(elem);
			if (filter != null) {
				group.addFollower(filter);
			}
		}
		return group;
	}

}
