package tester;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import junit.framework.Test;

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
import toni.druck.page.PageRenderer;
import toni.druck.renderer.DefaultPageRenderer;
import toni.druck.renderer.ToFilePageRenderer;

public class TestRenderer implements PageRenderer {

	private boolean inPrint = false;
	private Vector<TestRendererItem> out = new Vector<TestRendererItem>();

	public TestRenderer() {
		super();
	}
	
	public void print() {
		for(TestRendererItem item : out) {
			System.out.println(item.toString());
		}
	}

	private TestRendererItem newItem(Element elem) {
		TestRendererItem item = new TestRendererItem();
		item.setElement(elem);
		out.add(item);
		return item;
	}

	private TestRendererItem newItem(String elem) {
		TestRendererItem item = new TestRendererItem();
		item.setElementName(elem);
		out.add(item);
		return item;
	}

	public int getStatus() {
		return REAL_PRINT;
	}

	public void addExtension(Extension extension) {
	}

	public void print(Element elem) {
		if (!elem.isEnabled())
			return;
		if (!elem.isPrintable())
			return;

		if (!inPrint) {
			startDocument(elem.getPage());
		}
		printElement(elem);

	}

	public void printElement(Element elem) {
		printPre(elem);
		elem.printChilds(this);
	}

	public void startDocument(Page page) {
		if (inPrint) {
			endDocument();
		}
		inPrint = true;

		newItem(page.getName());

	}


	public void endDocument() {
		if (out != null) {
			newItem("endDocument");
		}
		inPrint = false;
	}

	
	public Dimension getAbsolutPosition(Element e) {
		Page p = e.getPage();
		int w = e.X();
		int h = e.Y();
		if (h > p.getHeight()) {
			throw new RuntimeException("Seite zu klein " + e.getName());
		}
		return new Dimension(w, p.getHeight() - h);
	}

	public void printPre(Element e) {
		Dimension apos = getAbsolutPosition(e);
		String text = e.getText();

		TestRendererItem item = newItem(e);
		item.setText(text);
		apos.height -= (e.getHeight() / 2);
		item.setPosition(apos.width + e.getPaddingX(),
				apos.height - e.getPaddingY());
		item.setDimension(e.getWidth(), e.getHeight());
	}

	public void newPage(int pagenr, Page page) {
		newItem(page.getName());
	}

	public void setOutput(OutputStream out) {
	}

	public void setOutput(String filename) throws FileNotFoundException {
	}

	public void printDefs(Element elem) {
	}

	public void include(String filename) throws IOException {
	}


}
