/*
 * Created on 07.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package toni.druck.page;



/*****
 * 
 * @author Thomas Nill
 * 
 * DataItem repräsentiert eine Einheit im Datenstrom,
 * der die Daten für den Report bereitstellt-
 * Jede Zeile einer Datendatei erzeugt ein DataItem.
 * 
 * Ein DataItem ist im Wesentlichen ein Array aus Strings,
 * Eine Zeile einer Datei wird durch | in die Fekdwerte aufgetrennt.
 * Der erste Eintrag im Array hat dabei eine besondere Bedeutung,
 * er wird als Command bezeichnet.
 * Über diesen command Anteil ist dem DataItem 
 * ein Abschnitt des Ausdruckes, eine Section zugeordnet,
 * wenn die Sction denselben Namen, wie der Command hat.
 * 
 * Gibt es keinen solchen Abschnitt, so ist dem DataItem kein
 * Abschnitt zugeordnet, sondern es versorgt über  {@link Verteiler}
 * die {@link Page} mit Daten.
 * 
 * DataItem mit besonderen Command print, include, layout, output
 * dienen der Steuerung des Druckvorganges.
 * endOfFile repräsentiert den Abschluss des Datenstromes
 *
 */

public class DataItem {
	public static final String PRINT = "print";
	public static final String INCLUDE = "include";
	public static final String LAYOUT = "layout";
	public static final String OUTPUT = "output";
	public static final String ENDOFFILE = "endOfFile";

	private String command = null;
	private String values[] = null;
	private Element section; // Abschnitt im Layout
	private boolean nextItemOfTheSameType = false; // Hat der nachfolgende Druckabschnitt denselben Namen? 
	private Page page; // LayoutPage für den die Daten des DataItems sind

	public DataItem(String text) {
		super();
		values = text.split("\\|", -1);
		command = values[0];
	}

	public DataItem(String text, DataItem item) {
		this(text);
		setPage(item.getPage());
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
		if (page != null) {
			setSection(page.getSection(getCommand()));
		}
	}

	public DataItem(String values[]) {
		this.values = new String[values.length];
		for (int i = 0; i < values.length; i++)
			this.values[i] = values[i];
		command = values[0];
	}

	public DataItem(String name, String value) {
		values = new String[2];
		values[0] = name;
		values[1] = value;
		command = name;
	}

	public boolean isNextItemOfTheSameType() {
		return nextItemOfTheSameType;
	}

	public void setNextItemOfTheSameType(boolean nextItemOfTheSameType) {
		this.nextItemOfTheSameType = nextItemOfTheSameType;
	}

	public boolean hasSection() {
		return (section != null);
	}

	public Element getSection() {
		return section;
	}

	public void setSection(Element section) {
		this.section = section;
	}

	public String getCommand() {
		return command;
	}

	public String getData(int pos) {
		if (pos >= values.length)
			return "";
		return values[pos];
	}

	@Override
	public String toString() {
		String erg = getClass().getSimpleName() + " c=[" + command + "] ";
		for (int i = 0; i < values.length; i++)
			erg += "[" + values[i] + "] [" + i + "]";
		return erg;
	}

	public int getSize() {
		return values.length;
	}

}
