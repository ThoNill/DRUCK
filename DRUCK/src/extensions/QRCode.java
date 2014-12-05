package extensions;

import java.io.PrintWriter;

import toni.druck.core2.Element;
import toni.druck.core2.Extension;
import toni.druck.core2.PageRenderer;
import toni.druck.renderer.PDFRenderer;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;

public class QRCode extends Extension {

	public QRCode() {
		super("extensions/qrcode.ps");
	}

	public boolean zustaendig(Element elem) {
		return elem instanceof toni.druck.elements.QRCode;
	}

	public void print(Element elem, PrintWriter out) {
		out.print(" (");
		out.print(elem.getText());
		out.print(") ");
		out.println(" () /qrcode /uk.co.terryburton.bwipp findresource exec ");

	}

	@Override
	public void print(Element elem, Document document, PdfContentByte cb,
			PageRenderer pageRenderer) {
		try {
			PDFRenderer renderer = (PDFRenderer) pageRenderer;
			BarcodeQRCode qrcode = new BarcodeQRCode(elem.getText(), 1, 1, null);
			Image img;
			img = qrcode.getImage();
			renderer.addImage(elem, img);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
