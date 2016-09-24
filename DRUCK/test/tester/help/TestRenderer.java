package tester.help;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;

public class TestRenderer implements PageRenderer {
	int pageCount = 0;
	List<Element> printedElements = null;

	public TestRenderer() {
		printedElements = new ArrayList<Element>();
	}

	public List<Element> getPrintedElements() {
		return printedElements;
	}

	public int getPageCount() {
		return pageCount;
	}

	@Override
    public void addExtension(Extension qrCode) {

	}

	@Override
    public void endDocument() {
	}

	@Override
    public int getStatus() {
		return 0;
	}

	@Override
    public void newPage(int pagenr,Page page) {
		pageCount++;

	}

	@Override
    public void print(Element elem) {
		printedElements.add(elem);

	}

	@Override
    public void printDefs(Element elem) {
		// TODO Auto-generated method stub

	}

	@Override
    public void setOutput(OutputStream out) {
		// TODO Auto-generated method stub

	}

	@Override
    public void setOutput(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
    public void startDocument(Page page) {
		// TODO Auto-generated method stub

	}

	@Override
    public void include(String filename) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
