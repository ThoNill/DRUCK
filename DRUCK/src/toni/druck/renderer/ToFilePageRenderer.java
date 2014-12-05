package toni.druck.renderer;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.jfree.chart.JFreeChart;

import toni.druck.chart.Chart;
import toni.druck.core2.Element;
import toni.druck.core2.Extension;
import toni.druck.core2.Page;

public class ToFilePageRenderer extends DefaultPageRenderer {
	protected PrintWriter out;
	protected boolean inPrint = false;
	private String codepage = "ISO-8859-1";

	public ToFilePageRenderer(String codepage) {
		setCodepage(codepage);
		out = new PrintWriter(System.out);

	}

	public String getCodepage() {
		return codepage;
	}

	public void setCodepage(String codepage) {
		this.codepage = codepage;
	}

	public void endDocument() {
		inPrint = false;
	}

	public void print(Element elem) {
		if (out != null) {
			out.println(elem.getName());
		}
		;
	}

	public void printDefs(Element elem) {
		// TODO Auto-generated method stub

	}

	public void setOutput(OutputStream out) {
		try {
			this.out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(out, getCodepage()), 1000));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setOutput(String name) throws FileNotFoundException {
		try {
			setOutput(new FileOutputStream(name));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void startDocument(Page page) {
		if (inPrint) {
			endDocument();
		}
		inPrint = true;
	}

	public int getStatus() {
		return ACCUMULATION;
	}

	public void newPage(int pagenr, Page page) {
	}

	public void addExtension(Extension qrCode) {
		// TODO Auto-generated method stub

	}

	public void include(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void printChart(Chart elem, JFreeChart chart) {
		// TODO Auto-generated method stub

	}
}
