package toni.druck.xml;

import java.util.Stack;

import org.apache.commons.beanutils.ConvertingWrapDynaBean;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import toni.druck.chart.Chart;
import toni.druck.elementRenderer.ElementRenderer;
import toni.druck.elements.ActionOnElement;
import toni.druck.elements.Filter;
import toni.druck.elements.Renderer;
import toni.druck.helper.SetAttributes;
import toni.druck.page.Action;
import toni.druck.page.ElementAction;
import toni.druck.page.Page;
import toni.druck.page.Verteiler;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Durchläuft eine JDOM2 Element Tree um eine {@link Page} aufzubauen.
 * 
 */
public class DruckWalker extends TreeWalker {
    private Page page;
    private Stack<Object> parents = new Stack<Object>();

    public Page getPage() {
        return page;
    }

    @Override
    public void walkAlong(Document doc) {
        super.walkAlong(doc);
        page.connectPrintListener();
    }

    @Override
    protected void bearbeite(Element elem) {
        // TODO Auto-generated method stub
        bearbeiteReferenz(elem,
                ((JDOMElementWithReference) elem).getReference());
    }

    private void bearbeiteReferenz(Element elem, Object reference) {
        if (reference instanceof Chart) {
            bearbeiteChart(elem, (Chart) reference);
        } else if (reference instanceof toni.druck.elements.Image) {
            bearbeiteDruckElement(elem, (toni.druck.page.Element) reference);
        } else if (reference instanceof toni.druck.page.Element) {
            bearbeiteDruckElement(elem, (toni.druck.page.Element) reference);
        } else if (reference instanceof Verteiler) {
            bearbeiteVerteiler(elem, (Verteiler) reference);
        } else if (reference instanceof Filter) {
            bearbeiteFilter(elem, (Filter) reference);
        } else {
            if (reference instanceof Action) {
                bearbeiteAction(elem, (Action) reference);
            }
            if (reference instanceof Renderer) {
                bearbeiteRenderer(elem, (Renderer) reference);
            }
            if (reference instanceof ActionOnElement) {
                bearbeiteElementAction(elem, (ActionOnElement) reference);
            }
        }

    }

    private void bearbeiteElementAction(Element elem, ActionOnElement action) {
        if (hasParent()) {
            String attributes[] = new String[] { "className" };
            setBeanAttributesWithName(elem, action, attributes);
            Object vo = parents.peek();
            if (vo instanceof toni.druck.standardElemente.StandardElement) {
                ElementAction er = action.createElementAction();
                setBeanAttributesExcept(elem, er, attributes);
                ((toni.druck.standardElemente.StandardElement) vo)
                        .addBeforPrint(er);
            }
        }
    }

    private void bearbeiteFilter(Element elem, Filter dummy) {
        page.addFilter(elem);
    }

    private void bearbeiteChart(Element elem, Chart chart) {
        String attributes[] = new String[] { "className", "variable", "width",
                "height", "name", "relX", "relY", "shiftY", "shiftX", "font",
                "fontsize", "align", "grayscale", "linewidth", "bordered",
                "filled", "paddingX", "paddingY" };
        chart.setPage(page);
        setBeanAttributesWithName(elem, chart, attributes);
        setBeanAttributesExcept(elem, chart.getClassFactory(), attributes);
        addToParentOrPage(chart);
    }

    private void bearbeiteRenderer(Element elem, Renderer renderer) {
        if (hasParent()) {
            String attributes[] = new String[] { "className" };
            setBeanAttributesWithName(elem, renderer, attributes);
            Object vo = parents.peek();
            if (vo instanceof toni.druck.standardElemente.StandardElement) {
                ElementRenderer er = renderer.createElementRenderer();
                setBeanAttributesExcept(elem, er, attributes);
                ((toni.druck.standardElemente.StandardElement) vo)
                        .setRenderer(er);
            }
        }
    }

    private void bearbeiteAction(Element elem, Action reference) {
        if (hasParent()) {
            setBeanAttributes(elem, reference);
            reference.setPage(page);
            Object vo = parents.peek();
            if (vo instanceof Verteiler) {
                Verteiler v = (Verteiler) vo;
                v.addAction(reference);
            }
        }
    }

    protected void bearbeiteVerteiler(Element elem, Verteiler reference) {
        setBeanAttributes(elem, reference);
        page.addVerteiler(reference);

    }

    public void bearbeiteDruckElement(Element elem,
            toni.druck.page.Element reference) {
        reference.setPage(page);
        setBeanAttributes(elem, reference);

        addToParentOrPage(reference);

    }

    private void addToParentOrPage(toni.druck.page.Element reference) {
        if (hasParent()) {
            ((toni.druck.page.Element) parents.peek()).addChild(reference);
        } else {
            if (reference.getName() != null) {
                page.addSection(reference);
            }
        }
    }

    protected boolean hasParent() {
        return parents.size() > 0;
    }

    private boolean find(String name, String names[]) {
        for (String n : names) {
            if (name.equals(n))
                return true;
        }
        return false;
    }

    private void setBeanAttributes(Element elem, Object reference) {
        SetAttributes.setBeanAttributes(elem, reference);
    }

    private void setBeanAttributesWithName(Element elem, Object reference,
            String attributnamen[]) {
        ConvertingWrapDynaBean bean = new ConvertingWrapDynaBean(reference);
        for (Object a : elem.getAttributes()) {
            if (a instanceof Attribute) {
                Attribute at = (Attribute) a;
                if (find(at.getName(), attributnamen)) {
                    bean.set(at.getName(), at.getValue());
                }
            }
        }

    }

    private void setBeanAttributesExcept(Element elem, Object reference,
            String ausnahmeAttribute[]) {
        ConvertingWrapDynaBean bean = new ConvertingWrapDynaBean(reference);

        for (Object a : elem.getAttributes()) {
            if (a instanceof Attribute) {
                Attribute at = (Attribute) a;
                if (!find(at.getName(), ausnahmeAttribute)) {
                    bean.set(at.getName(), at.getValue());
                }
            }
        }
    }

    @Override
    protected void goDown(Element elem) {
        if (hasParent()) {
            parents.pop();
        }
    }

    @Override
    protected void goUp(Element elem) {
        Object obj = ((JDOMElementWithReference) elem).getReference();
        if (obj instanceof toni.druck.page.Element) {
            parents.push(obj);
        }
        if (obj instanceof Page) {
            page = (Page) obj;
            setBeanAttributes(elem, page);
            page.setName(elem.getAttributeValue("name"));
        }
        if (obj instanceof Verteiler) {
            parents.push(obj);
        }
    }

}
