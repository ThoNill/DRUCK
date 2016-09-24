package extensions;

import java.io.PrintWriter;

import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PDFRenderer;

import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

/**
 * 
 * @author Thomas Nill
 * 
 *         Dient dazu, um Hacken zu malen
 * 
 */
public class Haken extends Extension {

    public Haken() {
        super("extensions/haken.ps");
    }

    @Override
    public boolean zustaendig(Element elem) {
        return elem instanceof toni.druck.elements.Haken;
    }

    // Ausgabe für Postscript
    @Override
    public void print(Element elem, PrintWriter out) {
        toni.druck.elements.Haken haken = (toni.druck.elements.Haken) elem;
        out.print(" ");
        out.print((haken.isVh()) ? "true" : "false");
        out.print(" ");
        out.print(" [ ");
        out.print(elem.getText());
        out.println(" ] ");
        Dimension apos = getAbsolutStartPosition(elem);
        out.print(" ");
        out.print((apos.width + elem.getPaddingX()) / 10.0);
        out.print(" cm ");
        out.print((apos.height - elem.getPaddingY()) / 10.0);
        out.print(" cm ");
        out.println(" ");
        out.println(elem.getLinewidth());
        out.println(" hakenschlagen ");
    }

    // Ausgabe für PDF
    @Override
    public void print(Element elem, Document document, PdfContentByte cb,
            PageRenderer pageRenderer) {
        PDFRenderer renderer = (PDFRenderer) pageRenderer;
        toni.druck.elements.Haken haken = (toni.druck.elements.Haken) elem;
        String text = haken.getText();
        boolean vertikal_oder_horizontal = !haken.isVh();
        if (text != null) {
            cb.setLineWidth(haken.getLinewidth());
            Dimension dim = setzeStartPosition(elem, cb, renderer);
            String[] snumber = text.trim().split(" +");
            for (int i = 0; i < snumber.length; i++) {
                float shift = 10.0f * Float.parseFloat(snumber[i]);
                dim = drawHakenLine(cb, shift, renderer, dim,
                        vertikal_oder_horizontal, elem);
                vertikal_oder_horizontal = !vertikal_oder_horizontal;
            }
            cb.stroke();
        }
    }

    private Dimension setzeStartPosition(Element elem, PdfContentByte cb,
            PDFRenderer renderer) {
        Dimension dim = getAbsolutStartPosition(elem);
        Dimension pdim = renderer.getDimensionAnpassung(dim, elem);
        cb.moveTo((float) (pdim.width + elem.getPaddingX()),
                (float) (pdim.height - elem.getPaddingY()));
        return dim;
    }

    private Dimension drawHakenLine(PdfContentByte cb, float shift,
            PDFRenderer renderer, Dimension pos,
            boolean vertikal_oder_horizontal, Element elem) {
        Dimension pos1 = new Dimension(pos);
        if (vertikal_oder_horizontal) {
            pos1.height += shift;
        } else {
            pos1.width += shift;
        }
        Dimension pos2 = renderer.getDimensionAnpassung(pos1, elem);
        cb.lineTo((float) (pos2.width + elem.getPaddingX()),
                (float) (pos2.height - elem.getPaddingY()));
        return pos1;
    }

    private Dimension getAbsolutStartPosition(Element e) {
        Page p = e.getPage();
        int w = e.X();
        int h = e.Y();
        if (h > p.getHeight()) {
            throw new IllegalArgumentException("Seite zu klein " + e.getName());
        }
        return new Dimension(w, p.getHeight() - h);
    }
}
