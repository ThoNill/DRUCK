package toni.druck.page;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import toni.druck.model.DataModel;

/*****
 * 
 * @author Thomas Nill
 * 
 * Repräsentiert ein Layout 
 * 
 *  
 */
public class Page extends EventAction {
	public  static final String FOOTER = "footer";
	public static final String HEADER = "header";
	public  static final String FIRST_HEADER = "first_header";

	private HashMap<String, Element> sections;
	private HashMap<String, Verteiler> verteiler = null;
	private HashMap<String, PrintController> controller;

	private int freePlace;
	private int pages;
	private boolean pageIsEmpty;
	private DataModel data;
	private int width = 210;
	private int height = 300;
	private String name;
	private int posy;
	private boolean firstPage = true;
	private Vector<org.jdom2.Element> filterElements = null;

	public Vector<org.jdom2.Element> getFilterElements() {
		return filterElements;
	}

	Vector<VerteilerConnector> connectMe = new Vector<VerteilerConnector>();

	int posx;

	public Page(String name) {
		this();
		setName(name);
	}

	public Page() {
		super();
		data = new DataModel();
		sections = new HashMap<String, Element>();
		verteiler = new HashMap<String, Verteiler>();
		controller = new HashMap<String, PrintController>();
		pages = 0;
		pageIsEmpty = true;
		firstPage = true;
	}

	public void addFilter(org.jdom2.Element elem) {
		if (filterElements == null) {
			filterElements = new Vector<org.jdom2.Element>();
		}
		filterElements.add(elem);
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public int getPosy() {
		return posy;
	}

	public int getPosx() {
		return posx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public boolean isLandscape() {
		return getHeight() < getWidth();
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Page getPage() {
		return this;
	}

	public boolean isPageIsEmpty() {
		return pageIsEmpty;
	}

	public void newPage(PageRenderer renderer) {
		initFreeSpace();
		posx = 0;
		posy = 0;
		if (!pageIsEmpty || pages == 0) {
			if (pages > 0) {
				renderer.newPage(pages + 1, this);
			}
			pages++;
			pageIsEmpty = true;
			fireEvent(new PrintEvent(PrintEvent.NEUE_SEITE, renderer));
		}
	}

	private void initFreeSpace() {
		freePlace = getHeight();
		if (pages == 0) {
			hoeheNichtBeruecksichtigen(FOOTER);
		}
		;
		freePlace -= getHeight(FOOTER);
		if (freePlace <= 0) {
			throw new RuntimeException("Page zu klein!");
		}
	}

	public Element getSection(String name) {
		return sections.get(name);
	}

	public PrintController getController(String name) {
		return controller.get(name);
	}

	public void printSectionOfTheItem(DataItem d, PageRenderer out) {

		PrintController s = getController(d.getCommand());
		if (s != null) {
			beachteErsteSeite(out);
			printWhenFreePlace(d, out, s);
		} else {
			Verteiler v = verteiler.get(d.getCommand());
			if (v != null) {
				v.fillValues(d);
				v.perform(d, out);
			}
		}
	}

	private void printWhenFreePlace(DataItem d, PageRenderer out,
			PrintController controller) {
		String aktualValues[] = null; // zum Zurücksichern der Datenwerte aus d
		Verteiler v = verteiler.get(d.getCommand());
		if (v != null) {
			aktualValues = v.getActualValues(); // alte Werte sichern
			v.fillValues(d); // Werte setzen
		}
		controller.prepareForPrint(); // prepare mit den neuen Werten 
		boolean platzDa = hasPlaceFor(controller, d); // entscheiden, ob genug Platz auf der Seite ist
		if (!platzDa) {
			if (v != null) v.restoreValues(aktualValues); // alte Werte kurz zurückschreiben
			printNewPage(out); 
			if (v != null) v.fillValues(d); // nun wieder mit den neuen Werten füllen
		}
		if (v != null) {
			v.perform(d, out);
		}
		printHeader(out);
		print(controller, out);
	}

	private void hoeheNichtBeruecksichtigen(String section) {
		Element s = sections.get(section);
		if (s != null) {
			s.setPageShiftHeight(0);
		}
	}

	public boolean printSection(String section, PageRenderer out) {
		Element s = sections.get(section);
		if (s != null) {
			print(s, out);
		}
		return (s != null);
	}

	private void print(PrintController controller, PageRenderer out) {
		if (controller != null && controller.isPrintable()) {
			pageIsEmpty = false;
			int h = controller.getPageShiftHeight();
			controller.print(out);
			addPosY(controller, h);
		}
	}

	public void addPosY(PrintController s, int h) {
		posy += h;
		freePlace -= h;
	}

	public void printHeader(PageRenderer out) {
		if (isPageIsEmpty()
				&& (!(pages == 1 && printSection(FIRST_HEADER, out)))) {
			printSection(HEADER, out);
		}
	}

	public void printFooter(PageRenderer out) {
		if (!isPageIsEmpty()) {
			int old = posy;
			posy = height - getHeight(FOOTER);
			printSection(FOOTER, out);
			posy = old;
		}
	}

	public int getHeight(String section) {
		Element s = sections.get(section);
		if (s != null && s.isPrintable()) {
			return s.getHeight();
		}
		return 0;
	}

	public boolean hasPlaceFor(PrintController section, DataItem item) {
		return (freePlace - section.getTestHeight(item) > 0);
	}

	public void addSection(Element section) {
		sections.put(section.getName(), section);
	}

	public void addVerteiler(Verteiler vert) {
		verteiler.put(vert.getName(), vert);
		vert.setDataModel(getData());
	}

	public void addPrintListener(String name, PrintListener listener) {
		connectMe.addElement(new VerteilerConnector(name, listener));
	}

	public void connectPrintListener() {
		for (VerteilerConnector c : connectMe) {
			connectPrintListener(c);
		}
	}

	private void connectPrintListener(VerteilerConnector con) {
		Verteiler v = verteiler.get(con.getName());
		if (v != null) {
			v.addListener(con.getListener());
		}
	}

	public DataModel getData() {
		return data;
	}

	public void finish(PageRenderer out) {
		printFooter(out);

	}

	public void printNewPage(PageRenderer out) {
		if (!isPageIsEmpty()) {
			printFooter(out);
			newPage(out);
			// printHeader(out);
		}
	}

	public void beachteErsteSeite(PageRenderer out) {
		if (firstPage) {
			firstPage = false;
			newPage(out);
			// printHeader(out);
		}
	}

	public void layout() {
		for (Element e : sections.values()) {
			e.layout();
		}
	}

	public void createController() {
		controller = new HashMap<String, PrintController>();
		for (Element e : sections.values()) {
			PrintController c = e;

			if (getSection(e.getName() + "_header") != null
					|| getSection(e.getName() + "_footer") != null) {
				PrintHeaderAndFooter phf = new PrintHeaderAndFooter(this,
						e.getName());
				this.addListener(phf);
				c = phf;
			}

			controller.put(e.getName(), c);

		}
	}

	public Verteiler getVerteiler(String name) {
		return verteiler.get(name);
	}

	public Set<String> getVerteilerNamen() {
		return verteiler.keySet();
	}

	public Set<String> getAbschnittNamen() {
		return sections.keySet();
	}

	public String getData(int index) {
		return data.get(index);
	}

	public String getData(String name) {
		return data.get(name);
	}

	public void putData(int index, String value) {
		data.put(index, value);
	}

	public void putData(String name, String value) {
		data.put(name, value);
	}

}
