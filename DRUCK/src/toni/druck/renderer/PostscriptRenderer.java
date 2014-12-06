package toni.druck.renderer;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.jfree.chart.JFreeChart;

import toni.druck.chart.Chart;
import toni.druck.chart.DruckChartFactory;
import toni.druck.elements.Hbox;
import toni.druck.elements.Image;
import toni.druck.elements.Label;
import toni.druck.elements.Line;
import toni.druck.elements.MultiLine;
import toni.druck.elements.PositionBox;
import toni.druck.elements.TextField;
import toni.druck.elements.Vbox;
import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;

public class PostscriptRenderer extends ToFilePageRenderer {
	static Logger logger = Logger.getLogger(PostscriptRenderer.class.getName());

	private Vector<Extension> extensions = new Vector<Extension>();
	int counter = 0;
	int save_restore = 0;
	int pageCount=0;

	public PostscriptRenderer(String codepage) {
		super(codepage);
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return REAL_PRINT;
	}

	public void addExtension(Extension extension) {
		extensions.addElement(extension);
	}

	protected void printChart(Chart elem, JFreeChart chart) {

		try {

			byte eps[] = createEPS(elem, chart);
			if (eps != null) {
				int boundingBox[] = new int[4];
				boundingBox[0] = 0;
				boundingBox[1] = 0;
				boundingBox[2] = elem.getWidth() * 10;
				boundingBox[3] = elem.getHeight() * 10;

				String t = new String(eps);

				gsave();
				printBorderString(elem);
				grestore();
				gsave();
				printClipString(elem);
				printImageBox(elem, t, boundingBox);
				grestore();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private byte[] createEPS(Chart elem, JFreeChart chart) throws IOException {
		if (chart != null) {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
			g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());

			g2d.setupDocument(byteArray, elem.getWidth() * 10,
					elem.getHeight() * 10); // 400pt
			// x
			// 200pt
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0,
					elem.getWidth() * 10, elem.getWidth() * 10);

			chart.draw(g2d, rectangle2d);
			g2d.finish();
			return byteArray.toByteArray();

		} else {
			return null;
		}
	}

	public void print(Element elem) {
		if (!elem.isEnabled())
			return;
		if (!elem.isPrintable())
			return;

		if (!inPrint) {
			startDocument(elem.getPage());
		}
		out.println();
		// infoStart(elem);
		if (elem instanceof Image) {
			printImage((Image) elem);
		} else if (elem instanceof Chart) {
			printChart((Chart) elem, DruckChartFactory.PS_THEMA);
		} else if (elem instanceof MultiLine) {
			printMultiline((MultiLine) elem);
		} else if (elem instanceof Line) {
			printLine((Line) elem);
		} else if (elem instanceof TextField || elem instanceof Vbox
				|| elem instanceof Hbox || elem instanceof PositionBox
				|| elem instanceof Label) {
			printElement(elem);
		} else if (elem instanceof Element) {
			printExtension((Element) elem);
		}
		;
		out.println();
		// infoEnd(elem);

	}

	private void infoEnd(Element elem) {
		if (elem.getName() != null) {
			out.println("%%End Element " + elem.getName());
		} else {
			out.println("%%End Element " + elem.getClass().getSimpleName());
		}
	}

	private void infoStart(Element elem) {
		if (elem.getName() != null) {
			out.println("%%Start Section " + elem.getName());
		} else {
			out.println("%%Start Element " + elem.getClass().getSimpleName());
		}
	}

	private void printImage(Image elem) {
		gsave();
		printBorderString(elem);
		grestore();
		gsave();
		printClipString(elem);
		String t = elem.getText();

		int boundingBox[] = elem.getBoundlingBox();

		printImageBox(elem, t, boundingBox);

		grestore();
	}

	private void printImageBox(Element elem, String t, int[] boundingBox) {
		int w = elem.getWidth();

		double v = ((double) w / (double) (boundingBox[2] - boundingBox[0])) * 2.8;

		out.println(" BeginEPSF ");
		printTranslateString(elem);
		out.println(" " + v + " " + v + "  scale "); // Scale to desired size
		out.println(" -" + boundingBox[0] + " -" + boundingBox[1]
				+ " translate  "); // Move to lower left of the

		out.println(t);
		out.println(" EndEPSF ");
	}

	public void printElement(Element elem) {
		logger.debug("print 1 [" + getClass().getName() + "] " + elem.getName());
		boolean needGSafe = needGSave(elem);
		if (needGSafe) {
			gsave();
		}
		;

		printPre(elem);
		logger.debug("print 2");
		elem.printChilds(this);
		logger.debug("print 3");
		printPost(elem);
		logger.debug("print 5");
		if (needGSafe) {
			grestore();
		}
		;
	}

	public boolean needGSave(Element elem) {
		return true; // hasFont(elem) || hasGrayscale(elem) ||
						// hasLineWidth(elem);
	}

	public void startDocument(Page page) {
		super.startDocument(page);
		try {
			out.println("%!PS-Adobe-2.0");
			out.print("%%BeginDocument ");
			out.println("" + page.getName() + counter);
			counter++;
			Extension.readIn(out, "extensions/default.ps");

			out.println("%% Page " + page.getName());
			printDefs(page);

			for (Extension ext : extensions) {
				ext.extend(out);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.println("%%Page: 1 1 ");
		out.println("%%BeginPageSetup ");
		out.println("%%EndPageSetup");
		out.println("/ISOfont findfont  10 scalefont setfont ");
		gsave();
		printLandscape(page);

	}

	private void printLandscape(Page page) {
		if (page.isLandscape()) {
			out.println(" 90 rotate ");
			out.println(" 0 cm " + (-page.getHeight() / 10.0)
					+ "  cm translate ");
		}
	}

	public void include(String filename) throws IOException {
		out.print(" false 0 startjob pop ");
		out.print("%%BeginDocument doc" + counter);
		counter++;
		Extension.readIn(out, filename);
		out.println("%%EndDocument");
	}

	public void printExtension(Element elem) {
		gsave();
		printBorderString(elem);
		printFilledString(elem);
		printRUPosString(elem);
		for (Extension ext : extensions) {
			if (ext.zustaendig(elem)) {
				ext.print(elem, out);
			}
		}
		grestore();
	}

	private void grestore() {
		out.print(" grestore ");
		save_restore--;
	}

	private void gsave() {
		out.print(" gsave ");
		save_restore++;
	}

	private void printDefs(Page page) {

	}

	public void endDocument() {
		if (out != null) {
			grestore();
			out.println();
			out.println("%%SR " + save_restore);
			out.println("%%Trailer");
			out.println("defaults restore");
			out.println("%%Pages: " + pageCount);
			out.println("%%EOF");
			out.println("%%EndDocument");
			out.close();
			out = new PrintWriter(System.out);
		}
		super.endDocument();
	}

	public void printPost(Element elem) {
	}

	public void printPre(Element elem) {
		printFixedText(elem);
		out.print(getTextString(elem));
	}

	public void printBorderString(Element elem) {
		if (elem.isBordered()) {
			out.print(" newpath \n 0.0 setgray \n ");
			printLinewidthString(elem);
			printPosString(elem);
			printREckString(elem);
			out.print(" stroke \n");
		} else {
			out.print(" 0.0 setgray ");

		}
	}

	public boolean hasFont(Element elem) {
		return (elem.getFont() != null || elem.getFontsize() > 0);
	}

	public void printFontString(Element elem) {
		String font = elem.getFont();
		if (elem.getFont() != null) {
			out.print(" /");
			out.print(font);
			out.print(" findfont \n ");
			out.print(elem.getFontsize());
			out.print(" scalefont \n setfont \n ");
			printLinewidthString(elem);
		} else if (elem.getFontsize() > 0) {
			// out.print(" currentfont /FontName get ");
			out.print("/ISOfont ");
			out.print(" findfont \n ");
			out.print(elem.getFontsize());
			out.print(" scalefont \n setfont \n ");
		} else {
			out.print(" ");

		}
	}

	public void printPosString(Element elem) {
		Dimension apos = getAbsolutPosition(elem);
		out.print(" ");
		out.print(apos.width / 10.0);
		out.print(" cm ");
		out.print(apos.height / 10.0);
		out.print(" cm moveto \n");
	}

	public void printLinePosString(Element elem) {
		Dimension apos = getAbsolutPosition(elem);
		out.print(" ");
		out.print((apos.width - elem.getPaddingX()) / 10.0);
		out.print(" cm ");
		out.print((apos.height - elem.getPaddingY()) / 10.0);
		out.print(" cm moveto \n");
	}

	public void printTranslateString(Element elem) {
		Dimension apos = getAbsolutPosition(elem);
		out.print(" ");
		out.print(apos.width / 10.0);
		out.print(" cm ");
		// out.print(apos.height / 10.0);
		out.print((apos.height - elem.getHeight()) / 10.0);
		out.print(" cm translate \n");
	}

	public void printRUPosString(Element elem) {
		Dimension apos = getAbsolutPosition(elem);
		out.print(" ");
		out.print(apos.width / 10.0);
		out.print(" cm ");
		out.print((apos.height - elem.getHeight()) / 10.0);
		out.print(" cm moveto \n");
	}

	public Dimension getAbsolutPosition(Element e) {
		Page p = e.getPage();
		int w = e.X();
		int h = e.Y();
		if (h > p.getHeight()) {
			throw new RuntimeException("Seite zu klein " + e.getName());
		}
		return new Dimension(w, p.getHeight() - h);
		// return new Dimension(w, 280 - h);
	}

	public void printFilledString(Element elem) {
		out.print(" ");
		if (elem.isFilled()) {
			out.print(" newpath \n ");
			printLinewidthString(elem);
			printGrayscaleString(elem);
			printPosString(elem);
			printREckString(elem);
			out.print(" fill \n");
		}
		;
	}

	public void printClipString(Element elem) {
		out.print(" ");
		out.print(" newpath \n ");
		printPosString(elem);
		printREckString(elem);
		out.print(" clip \n");

	}

	public void printFixedText(Element elem) {
		printFilledString(elem);
		printBorderString(elem);
		printFontString(elem);
	}

	public String getTextString(Element e) {
		String text = e.getText();
		if (text != null && !"".equals(text.trim())) {
			text = text.replaceAll("\\(", "\\(");
			text = text.replaceAll("\\)", "\\)");

			// text = text.replaceAll("\\(", "\\\\(");
			// text = text.replaceAll("\\)", "\\\\)");
			return " " + getTextPosString(e) + " "
					+ ((e.getWidth() - 2 * e.getPaddingX()) / 10.0) + " cm  ("
					+ text + ") " + e.getAlign() + "align \n";
		}
		return " ";
	}

	private String getTextPosString(Element e) {
		Dimension apos = getAbsolutPosition(e);
		apos.height -= (e.getHeight() / 2);
		return " " + ((apos.width + e.getPaddingX()) / 10.0) + " cm "
				+ ((apos.height - e.getPaddingY()) / 10.0) + " cm moveto \n";

	}

	public boolean hasGrayscale(Element elem) {
		return (elem.getGrayscale() < 100);
	}

	public void printGrayscaleString(Element elem) {
		if (elem.getGrayscale() < 100) {
			out.print(" ");
			out.print(elem.getGrayscale() / 100.0);
			out.print(" setgray \n");
		}
	}

	public boolean hasLineWidth(Element elem) {
		return (elem.getLinewidth() > 0);
	}

	public void printLinewidthString(Element elem) {
		int linewidth = elem.getLinewidth();
		out.print(" ");
		if (linewidth > 0) {
			out.print(elem.getLinewidth());
			out.print(" setlinewidth \n");
		}
	}

	public void printREckString(Element elem) {
		Dimension size = elem.getSize();
		out.print(" ");
		out.print((size.width / 10.0));
		out.print("  -");
		out.print((size.height / 10.0));
		out.print(" reck \n");
	}

	public void printTextPosString(MultiLine m, int i) {
		Dimension size = m.getSize();
		Dimension apos = getAbsolutPosition(m);
		apos.height += (size.height * (m.getRows() - i)) / (m.getRows() + 1);
		out.print(" ");
		out.print(" cm ");
		out.print((apos.height / 10.0));
		out.print(" cm moveto \n");
	}

	public void printMultiline(MultiLine m) {
		gsave();
		printClipString(m);
		printFixedText(m);
		out.println(" calculateTextHeight ");
		Dimension size = m.getSize();
		String texte[] = m.getTexte();
		int rows = m.getRows();
		for (int i = 0; i < texte.length && i < rows; i++) {
			String text = texte[i];
			if (text != null && !"".equals(text.trim())) {
				out.print(" " + getTextPosString(m, -(i + 1)) + " "
						+ ((size.width - 2 * m.getPaddingX()) / 10.0)
						+ " cm  (" + text + ") " + m.getAlign() + "align \n");
			}
		}
		grestore();
	}

	public String getTextPosString(MultiLine m, int i) {
		Dimension apos = getAbsolutPosition(m);
		// apos.height-= m.getHeight();
		apos.height -= m.getPaddingY();
		return " " + ((apos.width + m.getPaddingX()) / 10.0) + " cm "
				+ (apos.height / 10.0) + " cm  TextHeight " + i
				+ " mul add moveto \n";
	}

	public void printLine(Line m) {
		out.print("newpath \n 0.0 setgray \n ");
		printLinewidthString(m);
		printLinePosString(m);
		Dimension dim = m.getSize();
		out.print(" ");
		out.print((dim.width / 10.0));
		out.print(" cm ");
		out.print((dim.height / 10.0));
		out.print(" cm rlineto \n stroke \n");
	}

	public void newPage(int pagenr, Page page) {
		pageCount++;
		out.println("showpage ");
		out.println("%%Page: " + pageCount+ " " + pageCount);
		out.println("%%BeginPageSetup ");
		out.println("%%EndPageSetup");

		printLandscape(page);
	}
}
