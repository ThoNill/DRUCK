package tester.help;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;

public class TestRenderer implements PageRenderer {
	int pageCount = 0;
	Vector<Element> printedElements = null;

	public TestRenderer() {
		printedElements = new Vector<Element>();
	}

	public Vector<Element> getPrintedElements() {
		return printedElements;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void addExtension(Extension qrCode) {
		// TODO Auto-generated method stub

	}

	public void endDocument() {
		// TODO Auto-generated method stub

	}

	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void newPage(int pagenr,Page page) {
		pageCount++;

	}

	public void print(Element elem) {
		printedElements.add(elem);

	}

	public void printDefs(Element elem) {
		// TODO Auto-generated method stub

	}

	public void setOutput(OutputStream out) {
		// TODO Auto-generated method stub

	}

	public void setOutput(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	public void startDocument(Page page) {
		// TODO Auto-generated method stub

	}

	public void include(String filename) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
