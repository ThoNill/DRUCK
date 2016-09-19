package extensions;

import java.io.PrintWriter;

import org.apache.log4j.Logger;

import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PDFRenderer;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;

/**
 * 
 * @author Thomas Nill
 * 
 *         Dient der Ausgabe von OCR Code
 * 
 * 
 */
public class QRCode extends Extension {
    private static final Logger LOG = Logger.getLogger("QRCode");

    public QRCode() {
        super("extensions/qrcode.ps");
    }

    @Override
    public boolean zustaendig(Element elem) {
        return elem instanceof toni.druck.elements.QRCode;
    }

    // Ausgabe für Postscript
    @Override
    public void print(Element elem, PrintWriter out) {
        out.print(" (");
        out.print(elem.getText());
        out.print(") ");
        out.println(" () /qrcode /uk.co.terryburton.bwipp findresource exec ");

    }

    // Ausgabe für PDF
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
            LOG.error(e);
        } catch (DocumentException e) {
            LOG.error(e);
        }
    }

}
