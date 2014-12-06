package toni.druck.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import org.jdom2.Element;

import toni.druck.elements.Add;
import toni.druck.elements.Haken;
import toni.druck.elements.Hbox;
import toni.druck.elements.Image;
import toni.druck.elements.Label;
import toni.druck.elements.PositionBox;
import toni.druck.elements.Sum;
import toni.druck.elements.TextField;
import toni.druck.elements.Vbox;
import toni.druck.page.Action;
import toni.druck.page.Verteiler;

public class EditorXMLWalker extends DruckWalker {
	PrintWriter out;
	Vector<Verteiler> verteiler = new Vector<Verteiler>();
	Vector<toni.druck.page.Element> sections = new Vector<toni.druck.page.Element>();
	HashMap<String, String> clasnnames = new HashMap<String, String>();
	int shifty = 0;

	public EditorXMLWalker() {
		super();
		clasnnames.put("toni.druck.elements.Sum", "druck.Summierer");
		clasnnames.put("toni.druck.elements.Add", "druck.Addierer");
		clasnnames.put("toni.druck.elements.TextField",
				"druck.TextFieldComponent");
		clasnnames.put("toni.druck.elements.Label", "druck.LabelComponent");
		clasnnames
				.put("toni.druck.elements.Image", "components.ImageComponent");
		clasnnames.put("toni.druck.elements.Hbox", "druck.BoxComponent");
		clasnnames.put("toni.druck.elements.Vbox", "druck.BoxComponent");
		clasnnames.put("toni.druck.elements.Line", "druck.SectionComponent");
		clasnnames.put("toni.druck.elements.Haken", "druck.SectionComponent");
		clasnnames.put("toni.druck.elements.QRCode", "druck.SectionComponent");

		clasnnames.put("toni.druck.elements.PositionBox", "druck.BoxComponent");
		clasnnames.put("toni.druck.elements.MultiLine",
				"druck.SectionComponent");
		clasnnames.put("toni.druck.elements.OmrStriche",
				"druck.SectionComponent");
	}

	protected void bearbeiteVerteiler(Element elem, Verteiler reference) {
		super.bearbeiteVerteiler(elem, reference);
		verteiler.addElement(reference);
	}

	public void bearbeiteDruckElement(Element elem,
			toni.druck.page.Element reference) {
		super.bearbeiteDruckElement(elem, reference);
		if ((!hasParent()) && reference.getName() != null) {
			sections.addElement(reference);
		}

	}

	private void setDescendents(toni.druck.page.Element e,
			Vector<toni.druck.page.Element> descendents) {
		if (e.getChilds() != null) {
			for (toni.druck.page.Element c : e.getChilds()) {
				descendents.addElement(c);
				setDescendents(c, descendents);
			}
		}
	}

	public void printParameterOfElement(toni.druck.page.Element e) {
		if (e instanceof TextField) {
			field(((TextField) e).getVariable());
		}
		if (e instanceof Label) {
			stringProperty("text", e.getText());
		}
		if (e instanceof Image) {
			stringProperty("image", ((Image) e).getImage());
		}
		booleanProperty("bordered", e.isBordered());
		booleanProperty("filled", e.isFilled());
		intProperty("grayscale", e.getGrayscale());
		intProperty("linewidth", e.getLinewidth());
		doubleProperty("width", e.getWidth());
		doubleProperty("height", e.getHeight());
		relXY(e);
		font(e);
	}

	private void stringProperty(String propertyName, String value) {
		property(propertyName, value, "string");
	}

	private void booleanProperty(String propertyName, boolean value) {
		property(propertyName, (value) ? "true" : "false", "boolean");
	}

	private void intProperty(String propertyName, int value) {
		property(propertyName, Integer.toString(value), "int");
	}

	private void doubleProperty(String propertyName, int value) {
		property(propertyName, Integer.toString(value) + ".0", "double");
	}

	private void field(String name) {
		out.println("<void id=\"FieldName0\" property=\"field\"> ");
		out.println("<void property=\"name\"> ");
		out.print("<string>");
		out.print(name);
		out.println("</string> ");
		out.println("</void> ");
		out.println("</void> ");
		out.println("<void property=\"field\"> ");
		out.println("<object idref=\"FieldName0\"/> ");
		out.println("</void> ");
	}

	private void relXY(toni.druck.page.Element e) {
		out.println("<void property=\"trans\"> ");
		out.println("<void property=\"translateX\"> ");
		out.println("<double>" + (e.shiftX() + (e.getWidth() / 2))
				+ "</double> ");
		out.println("</void> ");
		out.println("<void property=\"translateY\"> ");
		out.println("<double>" + (e.shiftY() + (e.getHeight() / 2))
				+ "</double> ");
		out.println("</void> ");
		out.println("</void> ");
	}

	private void font(toni.druck.page.Element e) {
		out.println("<void property=\"font\">  ");
		out.println("<object class=\"java.awt.Font\">  ");
		out.println("<string>Dialog</string>  ");
		out.println("<int>0</int>  ");
		out.println("<int>" + 4 + "</int>  ");
		out.println("</object>  ");
		out.println("</void>  ");
	}

	public void printXML(String filename) throws IOException {
		printXML(new FileOutputStream(filename));

	}

	public void printXML(OutputStream ostream) throws IOException {
		printXML(new PrintWriter(new OutputStreamWriter(ostream, "UTF-8")));
	}

	public void printXML(PrintWriter out) throws IOException {
		this.out = out;
		getPage().layout();
		int nr = 1;
		startPage();
		startComponents();
		for (toni.druck.page.Element e : sections) {
			shifty += e.getHeight() / 2;
			startSection();
			startChilds();

			Vector<toni.druck.page.Element> descendents = new Vector<toni.druck.page.Element>();
			setDescendents(e, descendents);
			e.layout();
			for (toni.druck.page.Element c : descendents) {
				if (erlaubt(c)) {
					startClass(c.getClass());
					printParameterOfElement(c);
					endClass();
				}
			}

			endChilds();
			printParameterOfElement(e);
			stringProperty("name", e.getName());
			intProperty("nr", nr);
			nr++;
			endSection();

		}
		endComponents();
		startKomponentenOhneGrafik();
		for (Verteiler v : verteiler) {
			startClass("druck.Verteiler");
			stringProperty("name", v.getName());
			stringProperty("fields", v.getFields());
			endClass();

			if (v.getActions() != null) {
				for (Action a : v.getActions()) {
					startClass(a.getClass());
					stringProperty("verteiler", v.getName());

					if (a instanceof Sum) {
						Sum s = (Sum) a;
						stringProperty("clearAt", s.getClearAt());
						stringProperty("item", s.getItem());
						stringProperty("sum", s.getSum());
					}

					if (a instanceof Add) {
						Add s = (Add) a;
						stringProperty("fields", s.getFields());
						stringProperty("result", s.getResult());
					}

					endClass();
				}
			}
		}
		endKomponentenOhneGrafik();
		endPage();
		out.close();
	}

	private boolean erlaubt(toni.druck.page.Element e) {
		if (e instanceof TextField)
			return true;
		if (e instanceof Image)
			return true;
		if (e instanceof Label) {
			Label l = (Label) e;
			if (l.getText() == null && (!e.isBordered()) && (!e.isFilled())) {
				return false;
			}
			return true;
		}
		if (e instanceof Haken)
			return false;
		if ((e instanceof Vbox) && (!e.isBordered()) && (!e.isFilled()))
			return false;
		if ((e instanceof Hbox) && (!e.isBordered()) && (!e.isFilled()))
			return false;
		if ((e instanceof PositionBox) && (!e.isBordered()) && (!e.isFilled()))
			return false;

		return true;
	}

	public void startPage() {
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<java version=\"1.6.0_21\" class=\"java.beans.XMLDecoder\">\n<object class=\"druck.DruckDataInStore\"> ");
	};

	public void startClass(Class c) {
		startClass(changeClassName(c));
	}

	private String changeClassName(Class c) {
		String s = clasnnames.get(c.getCanonicalName());
		if (s == null) {
			s = c.getCanonicalName();
			System.out.println(s);
		}
		return s;
	}

	public void startComponents() {
		out.println("<void property=\"components\">\n<object class=\"java.util.Vector\">");
	}

	public void startSection() {
		out.println("<void method=\"add\"> ");
		out.println("<object class=\"druck.SectionComponent\"> ");
	}

	public void startChilds() {
		out.println("<void property=\"childs\"> ");
	}

	public void endChilds() {
		out.println("</void>");
	}

	public void endSection() {
		out.println("</object></void> ");
	}

	public void endComponents() {
		out.println("</object> </void> ");
	}

	public void startKomponentenOhneGrafik() {
		out.println("<void property=\"komponentenOhneGrafik\">  <object class=\"java.util.Vector\"> ");
	}

	public void startClass(String classname) {
		out.println("<void method=\"add\">");
		out.println("<object class=\"" + classname + "\">");
	};

	public void property(String propertyName, String propertyValue, String type) {
		out.println("<void property=\"" + propertyName + "\">");
		out.println("<" + type + ">" + propertyValue + "</" + type
				+ "></void> ");
	}

	public void endClass() {
		out.println("</object></void> ");
	}

	public void endKomponentenOhneGrafik() {
		out.println("</object></void> ");
	}

	void endPage() {
		out.print(" </object></java> ");
	}

}
