package extensions;

import java.io.PrintWriter;

import toni.druck.core2.Element;
import toni.druck.core2.Extension;
import toni.druck.core2.Page;
import toni.druck.core2.PageRenderer;
import toni.druck.renderer.PDFRenderer;

import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

public class Haken extends Extension {

	public Haken() {
		super("extensions/haken.ps");
	}

	public boolean zustaendig(Element elem) {
		return elem instanceof toni.druck.elements.Haken;
	}

	public void print(Element elem, PrintWriter out) {
		toni.druck.elements.Haken haken = (toni.druck.elements.Haken) elem;
		out.print(" ");
		out.print((haken.isVh()) ? "true" : "false");
		out.print(" ");
		out.print(" [ ");
		out.print(elem.getText());
		out.println(" ] ");
		Dimension apos = getAbsolutPosition(elem);
		out.print(" ");
		out.print((apos.width + elem.getPaddingX()) / 10.0);
		out.print(" cm ");
		out.print((apos.height - elem.getPaddingY()) / 10.0);
		out.print(" cm ");
		out.println(" ");
		out.println(elem.getLinewidth());
		out.println(" hakenschlagen ");
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

	@Override
	public void print(Element elem, Document document, PdfContentByte cb,
			PageRenderer pageRenderer) {
		PDFRenderer renderer = (PDFRenderer) pageRenderer;
		toni.druck.elements.Haken haken = (toni.druck.elements.Haken) elem;
		String text = haken.getText();
		boolean vh = !haken.isVh();
		if (text != null) {
			// cb.saveState();
			cb.setLineWidth(haken.getLinewidth());
			Dimension dim = getAbsolutPosition(elem);
			Dimension pdim = renderer.getDimensionAnpassung(dim, elem);
			cb.moveTo((float) (pdim.width + elem.getPaddingX()),
					(float) (pdim.height - elem.getPaddingY()));
			String snumber[] = text.trim().split(" +");
			for (int i = 0; i < snumber.length; i++) {
				float shift = 10.0f * Float.parseFloat(snumber[i]);
				dim = printHakenString(cb, shift, renderer, dim, vh, elem);
				vh = !vh;
			}
			cb.stroke();
			// cb.restoreState();
		}
	}

	private Dimension printHakenString(PdfContentByte cb, float shift,
			PDFRenderer renderer, Dimension pos, boolean vh, Element elem) {
		Dimension pos1 = new Dimension(pos);
		if (vh) {
			pos1.height += shift;
		} else {
			pos1.width += shift;
		}
		Dimension pos2 = renderer.getDimensionAnpassung(pos1, elem);
		cb.lineTo((float) (pos2.width + elem.getPaddingX()),
				(float) (pos2.height - elem.getPaddingY()));
		return pos1;
	}

}
