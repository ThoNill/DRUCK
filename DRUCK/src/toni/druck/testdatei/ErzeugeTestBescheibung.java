package toni.druck.testdatei;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import toni.druck.elements.Hbox;
import toni.druck.elements.TextField;
import toni.druck.page.Element;
import toni.druck.page.Page;
import toni.druck.page.Verteiler;


/*****
 * 
 * @author Thomas Nill
 * 
 * Erzeugen einer Testbeschreibung aus den Daten der {@link Verteiler} einer {@link Page}
 * 
 */
public class ErzeugeTestBescheibung {
	private HashMap<String, Verteiler> feld2verteiler = new HashMap<String, Verteiler>();
	private HashMap<String, Set<Verteiler>> abschnit2verteiler = new HashMap<String, Set<Verteiler>>();
	private Set<Verteiler> einmalig = new HashSet<Verteiler>();
	private Set<Verteiler> mehrfach = new HashSet<Verteiler>();

	public void analysierePage(Page page) {
		analysiereVerteiler(page);
		analysiereAbschnitte(page);
		bestimmeEinmalig();
	}

	private void bestimmeEinmalig() {
		Set<Verteiler> headerVerteiler = abschnit2verteiler.get("header");
		Set<Verteiler> footerVerteiler = abschnit2verteiler.get("footer");

		if (headerVerteiler != null) {
			einmalig.addAll(headerVerteiler);
		}
		if (footerVerteiler != null) {
			einmalig.addAll(footerVerteiler);
		}
	}

	private void analysiereAbschnitte(Page page) {
		for (String name : page.getAbschnittNamen()) {
			analysiereEinzelnenAbschnitt(page.getSection(name));
		}

	}

	private void analysiereEinzelnenAbschnitt(Element section) {
		String name = section.getName();

		Set<Verteiler> verteiler = new HashSet<Verteiler>();
		analysiereElement(verteiler, section);
		if (verteiler.size() > 0) {
			abschnit2verteiler.put(name, verteiler);
		}
	}

	private void analysiereElement(Set<Verteiler> verteiler, Element element) {
		if (element instanceof TextField) {
			TextField tf = (TextField) element;
			String var = tf.getVariable();
			Verteiler v = feld2verteiler.get(var);
			verteiler.add(v);
		} else {
			if (element.getChilds() != null) {
				for (Element child : element.getChilds()) {
					analysiereElement(verteiler, child);
				}
			}
		}

	}

	private void analysiereVerteiler(Page page) {
		// TODO Auto-generated method stub
		for (String name : page.getVerteilerNamen()) {
			analysiereEinzelnenVerteiler(page, page.getVerteiler(name));
		}

	}

	private void analysiereEinzelnenVerteiler(Page page, Verteiler verteiler) {
		if (verteiler.getFields() != null) {
			String feldname[] = verteiler.getFields().split(" *, *");
			for (String fName : feldname) {
				feld2verteiler.put(fName, verteiler);
			}
		}
		String name = verteiler.getName();
		if (page.getSection(name + "_header") != null) {
			mehrfach.add(verteiler);
		}
	}

	public void erzeugeBeschreibung(Page page, PrintWriter out) {
		for (Verteiler v : einmalig) {
			if (v != null) {
				out.println(v.getName());
			}
		}
		for (String name : page.getVerteilerNamen()) {
			Verteiler v = page.getVerteiler(name);
			if (!einmalig.contains(v) && !mehrfach.contains(v)) {
				out.print(v.getName());
				out.print(":");
				out.println(10);
			}
		}
		for (Verteiler v : mehrfach) {
			if (v != null) {
				out.print(v.getName());
				out.print(":");
				out.println(100);
			}
		}
		out.close();
	}

	public void analysiereUndBeschreibe(Page page, PrintWriter out) {
		analysierePage(page);
		erzeugeBeschreibung(page, out);
	}
}
