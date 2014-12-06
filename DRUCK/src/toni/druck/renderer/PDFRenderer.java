package toni.druck.renderer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import toni.druck.chart.Chart;
import toni.druck.chart.DruckChartFactory;
import toni.druck.elements.Image;
import toni.druck.elements.Line;
import toni.druck.elements.MultiLine;
import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFRenderer extends DefaultPageRenderer {
	static Logger logger = Logger.getLogger("PDFRenderer");

	private Vector<Extension> extensions = new Vector<Extension>();

	PdfWriter writer;

	PdfContentByte cb;

	Document document = null;

	int fontsize = 10;

	boolean inPrint = false;

	BaseFont bfont = null;

	OutputStream out = null;

	String outputFileName = null;

	private float scaleX = 1.0f;
	private float scaleY = 1.0f;

	public PDFRenderer() {
		super();
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public void addExtension(Extension extension) {
		extensions.addElement(extension);
	}

	public void print(Element elem) {
		if (!elem.isEnabled())
			return;
		if (!elem.isPrintable())
			return;

		if (!inPrint) {
			inPrint = true;
			elem.getPage().layout();
			startDocument(elem.getPage());
		}
		if (elem instanceof MultiLine) {
			printMultiline((MultiLine) elem);
		} else if (elem instanceof Image) {
			print((Image) elem);
		} else if (elem instanceof Chart) {
			printChart((Chart) elem, DruckChartFactory.PDF_THEMA);
		} else if (elem instanceof Line) {
			print((Line) elem);
		} else if (elem instanceof StandardElement) {
			print((StandardElement) elem);
		}
	}

	protected void printChart(Chart elem, JFreeChart chart) {
		if (chart != null) {
			Dimension apos = getAbsolutPositionForPdf(elem);
			Dimension size = getSizeForPdf(elem);

			PdfTemplate template = cb.createTemplate((float) size.width,
					(float) size.height);
			Graphics2D graphics2d = new PdfGraphics2D(template,
					(float) size.width, (float) size.height,
					new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, size.width,
					size.height);

			chart.draw(graphics2d, rectangle2d);

			graphics2d.dispose();
			cb.addTemplate(template, (float) apos.width,
					(float) (apos.height - size.height));
		}

	}

	public void print(StandardElement elem) {

		try {
			// elem.styleYou();
			printPre(elem);
			// elem.printSections(this);
			boolean istBearbeitet = false;
			for (Extension ext : extensions) {
				if (ext.zustaendig(elem)) {
					ext.print(elem, document, cb, this);
					istBearbeitet = true;
				}
			}
			if (!istBearbeitet) {
				printTextElement(elem);
				printChilds(elem);
				printPost(elem);
			}
			;

		} catch (Exception ex) {
			logger.error("An Exception occured: " + ex.getMessage());
		}

	}

	/*
	 * public void printChilds(Element elem) { Vector v = elem.getChilds(); int
	 * anz = v.size();
	 * 
	 * for (int i = 0; i < anz; i++) {
	 * 
	 * Object obj = v.elementAt(i);
	 * 
	 * if (obj instanceof Element) {
	 * 
	 * ((Element) obj).print(this);
	 * 
	 * } // end if
	 * 
	 * } // end for }
	 */
	public void printChilds(Element elem) {
		elem.printChilds(this);
	}

	public void setPage(Page page) {
		if (document != null) {
			document.newPage();
			document.setPageSize(getPageSize(page));
		}
	}

	public void startDocument(Page page) {

		endDocument();

		try {
			Rectangle size = getPageSize(page);
			document = new Document(size);
			writer = PdfWriter.getInstance(document, getOutputStream(page));
			document.open();
			cb = writer.getDirectContent();

			scaleContext();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void scaleContext() {
		AffineTransform scale = AffineTransform.getScaleInstance(getScaleX(),
				getScaleY());
		cb.concatCTM(scale);
		cb.setTextMatrix(scale);
	}

	private Rectangle getPageSize(Page page) {
		return (page.isLandscape()) ? PageSize.A4.rotate() : PageSize.A4;
	}

	public void endDocument() {
		try {
			if (document != null) {
				out = null;
				outputFileName = null;
				cb = null;
				writer = null;
				document.close();
				document = null;
			}
		} catch (Exception ex) {

		}
	}

	public void printPost(Element elem) {
		if (elem instanceof Page) {
			printPost((Page) elem);
			return;
		}

		// cb.restoreState();

	}

	public void printPre(Element elem) {
		if (elem instanceof Page) {
			printPre((Page) elem);
			return;
		}

		// cb.saveState();

	}

	public void setLinewidth(StandardElement elem) {
		int linewidth = elem.getLinewidth();
		if (linewidth > 0) {
			cb.setLineWidth(linewidth);
		}
	}

	public void print(Line m) {

		Dimension apos = getAbsolutPositionForPdf(m);
		Dimension size = getSizeForPdf(m);

		cb.moveTo((float) apos.width, (float) apos.height);
		cb.lineTo((float) (apos.width + size.width), (float) apos.height);
		cb.stroke();

	}

	public void print(Image elem) {

		try {

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte b[] = new byte[80];
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					elem.getName());
			int anz = in.read(b);
			while (anz > 0) {
				buffer.write(b, 0, anz);
				anz = in.read(b);
			}
			in.close();

			com.itextpdf.text.Image img = com.itextpdf.text.Image
					.getInstance(buffer.toByteArray());

			addImage(elem, img);
		} catch (BadElementException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void addImage(Element elem, com.itextpdf.text.Image img)
			throws DocumentException {
		Dimension apos = getAbsolutPositionForPdf(elem);
		Dimension size = getSizeForPdf(elem);

		img.setAbsolutePosition((float) apos.width,
				(float) (apos.height - size.height));
		img.scaleToFit((float) size.width, (float) size.height);

		cb.addImage(img);
	}

	Dimension getAbsolutPositionForPdf(Element elem) {
		return getDimensionAnpassung(getAbsolutPosition(elem), elem);
	}

	public Dimension getDimensionAnpassung(Dimension dim, Element e) {
		Dimension apos = new Dimension(dim);
		// double vw = 595.0 / 210.0;
		// double vh = 842.0 / 295.0;
		apos.width = calcWidthAnpassung(e, apos.width);
		apos.height = calcHeightAnpassung(e, apos.height);
		return apos;
	}

	private int calcHeightAnpassung(Element e, double h) {
		double vh = PageSize.A4.getHeight() / e.getPage().getHeight();
		return (int) (h * vh);
	}

	private int calcWidthAnpassung(Element e, double w) {
		double vw = PageSize.A4.getWidth() / e.getPage().getWidth();
		return (int) (w * vw);
	}

	Dimension getSizeForPdf(Element elem) {
		return getDimensionAnpassung(
				new Dimension(elem.getSize().width, elem.getSize().height),
				elem);
	}

	public void setPage(int page) {

	}

	public void printPre(Page page) {
		// cb.saveState();
	}

	public void printPost(Page page) {
		// cb.restoreState();
		document.newPage();
	}

	public Dimension getAbsolutPosition(Element e) {
		Page p = e.getPage();
		int w = e.X();
		int h = e.Y();
		if (h > p.getHeight()) {
			throw new RuntimeException("Seite zu klein " + e.getName());
		}
		return new Dimension(w, e.getPage().getHeight() - h);
		// return new Dimension(w, p.getHeight() - h);
		// return new Dimension(w, 280 - h);
	}

	public void printTextElement(StandardElement elem) throws Exception {
		Dimension apos = getAbsolutPositionForPdf(elem);
		Dimension size = getSizeForPdf(elem);

		setLinewidth(elem);
		drawFilledBox(elem, apos, size);
		drawBorderedBox(elem, apos, size);
		setFontAndSize(elem);

		drawText(elem, apos, size);

	}

	private void drawText(StandardElement elem, Dimension apos, Dimension size) {
		float ypos = (float) (apos.height - (size.height / 2) - calcHeightAnpassung(
				elem, elem.getPaddingY()));
		drawText(elem, apos, size, ypos, elem.getText());
	}

	private void drawText(StandardElement elem, Dimension apos, Dimension size,
			float ypos, String text) {
		if (text != null) {
			cb.setColorFill(BaseColor.BLACK);
			cb.beginText();
			float xpos = (float) apos.width
					+ calcWidthAnpassung(elem, elem.getPaddingX());
			float w = (float) size.width - 2
					* calcWidthAnpassung(elem, elem.getPaddingX());
			int ialign = PdfContentByte.ALIGN_LEFT;

			if ("r".equals(elem.getAlign())) {
				ialign = PdfContentByte.ALIGN_RIGHT;
				xpos = (float) (apos.width + w);
			} else if ("c".equals(elem.getAlign())) {
				ialign = PdfContentByte.ALIGN_CENTER;
				xpos = (float) (apos.width + (w / 2));
			}

			cb.showTextAligned(ialign, text, xpos, ypos, 0);
			cb.endText();
		}
	}

	private void drawBorderedBox(StandardElement elem, Dimension apos,
			Dimension size) {
		if (elem.isBordered()) {
			cb.newPath();
			cb.setColorFill(BaseColor.BLACK);
			cb.rectangle((float) apos.width,
					(float) (apos.height - size.height), (float) size.width,
					(float) size.height);
			cb.stroke();
		}
	}

	private void drawFilledBox(StandardElement elem, Dimension apos,
			Dimension size) {
		if (elem.isFilled()) {
			cb.newPath();
			cb.setGrayFill(elem.getGrayscale() / 100.0f);
			cb.rectangle((float) apos.width,
					(float) (apos.height - size.height), (float) size.width,
					(float) size.height);
			cb.fill();
		}
	}

	private void setFontAndSize(StandardElement elem) throws DocumentException,
			IOException {
		String text = elem.getText();
		if (text != null) {
			int fontsize = elem.getFontsize();

			if (fontsize > 0) {
				String sfont = elem.getFont();

				String font = BaseFont.COURIER;
				if ("ISOfont".equals(sfont))
					font = BaseFont.COURIER;
				if ("ISOfontBold".equals(sfont))
					font = BaseFont.COURIER_BOLD;
				if ("Helvetica".equals(sfont))
					font = BaseFont.HELVETICA;
				if ("HelveticaBold".equals(sfont))
					font = BaseFont.HELVETICA_BOLD;
				if ("Times".equals(sfont))
					font = BaseFont.TIMES_ROMAN;
				if ("TimesBold".equals(sfont))
					font = BaseFont.TIMES_BOLD;

				BaseFont bf = BaseFont.createFont(font, BaseFont.CP1252,
						BaseFont.NOT_EMBEDDED);

				cb.setFontAndSize(bf, fontsize);
				bfont = bf;

			}
		}
	}

	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void include(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	public void newPage(int pagenr, Page page) {
		// TODO Auto-generated method stub
		if (document != null) {
			document.newPage();
			document.setPageSize(getPageSize(page));
			document.setPageCount(pagenr);

			scaleContext();
		}
	}

	public void printDefs(Element elem) {
		// TODO Auto-generated method stub

	}

	public void setOutput(OutputStream out) {
		this.out = out;
	}

	public void setOutput(String filename) throws FileNotFoundException {
		outputFileName = filename.replaceAll("\\.ps$", ".pdf");
	}

	private OutputStream getOutputStream(Page page) throws IOException {
		if (out == null) {
			if (outputFileName == null && page != null) {
				outputFileName = page.getName() + ".pdf";
			}
			out = new FileOutputStream(outputFileName);
		}
		return out;
	}

	public void printMultiline(MultiLine m) {
		// gsave();
		// printClipString(m);
		// printFixedText(m);
		cb.saveState();
		try {
			setFontAndSize(m);
			Dimension apos = getAbsolutPositionForPdf(m);
			Dimension size = getSizeForPdf(m);
			drawBorderedBox(m, apos, size);
			drawFilledBox(m, apos, size);
			// defineClipBox(m, apos, size);
			String texte[] = m.getTexte();
			int rows = m.getRows();
			int fontSize = m.getFontsize();
			float fh = (bfont.getAscentPoint("Xg", fontSize) - bfont
					.getDescentPoint("Xg", fontSize)) * 1.4f;
			float ypos = (float) (apos.height - fh);
			for (int i = 0; i < texte.length && i < rows; i++) {
				String text = texte[i];
				if (text != null && !"".equals(text.trim())) {
					drawText(m, apos, size, ypos, text);
				}
				ypos = ypos - fh;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb.restoreState();
		// grestore();
	}

}
