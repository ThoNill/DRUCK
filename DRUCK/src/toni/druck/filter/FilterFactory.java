package toni.druck.filter;

import java.util.List;
import java.util.Vector;

import org.jdom2.Element;

import toni.druck.helper.ClassFactory;
import toni.druck.helper.SetAttributes;

/**
 * 
 * @author Thomas Nill
 * 
 * @see Filter Fabrik, die aus einem Element der XML Layoutbeschreibung einen
 *      Filter erzeugt
 * 
 */

public class FilterFactory {

    // erzeugt einen einzelnen Filter
    public static Filter getFilter(Element elem) {
        Filter f = (Filter) ClassFactory.getInstance(elem
                .getAttributeValue("filterClass"));
        if (f != null) {
            SetAttributes.setBeanAttributes(elem, f);
        }
        return f;
    }

    // erzeugt einen verkettete Filter Gruppe

    public static FilterGroup getFilterGroup(List<Element> elements) {
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
