/*
 * Created on 07.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package toni.druck.core2;


/*****
 * 
 * Eine Aktion die während des erstellens eines Ausdruckes aufgerufen wird
 * z.Bsp. Zählen der Seiten, addieren von Werten
 * 
 * Object Interface
 * 
 * @author Thomas Nill
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
	private Element section;
	private boolean nextItemOfTheSameType = false;
	private Page page;

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
