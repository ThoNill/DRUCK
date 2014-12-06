package extensions;

import java.io.PrintWriter;

import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PDFRenderer;

import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

public class Omr extends Extension {
	static int OMR_X = 6;
	static int OMR_Y = 120;
	static int OMR_LENGTH = 6;
	static int OMR_YGAP = 5;
	static float OMR_STRICHSTAERKE = 3.0f;

	public Omr() {
		super("extensions/omr.ps");
	}

	@Override
	public boolean zustaendig(Element elem) {
		return elem instanceof toni.druck.elements.OmrStriche;
	}

	@Override
	public void print(Element elem, PrintWriter out) {
		out.print(" [ ");
		out.print(elem.getText());
		out.println(" ] omrstriche ");
	}

	@Override
	public void print(Element elem, Document document, PdfContentByte cb,
			PageRenderer pageRenderer) {
		PDFRenderer renderer = (PDFRenderer) pageRenderer;
		String text = elem.getText();
		if (text != null) {
			String snumber[] = text.trim().split(" +");
			for (int i = 0; i < snumber.length; i++) {
				int n = Integer.parseInt(snumber[i]);
				printOmrString(cb, n, renderer, elem);
			}
		}
	}

	private void printOmrString(PdfContentByte cb, int n, PDFRenderer renderer,
			Element elem) {
		int y = OMR_Y - (n - 1) * OMR_YGAP;
		cb.saveState();
		cb.setLineWidth(OMR_STRICHSTAERKE);
		Dimension dim = renderer.getDimensionAnpassung(new Dimension(OMR_X, y),
				elem);
		cb.moveTo((float) dim.width, (float) dim.height);
		dim = renderer.getDimensionAnpassung(new Dimension(OMR_X + OMR_LENGTH,
				y), elem);
		cb.lineTo((float) dim.width, (float) dim.height);
		cb.stroke();
		cb.restoreState();
	}

}
