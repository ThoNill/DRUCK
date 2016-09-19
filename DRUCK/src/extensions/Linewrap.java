package extensions;

import java.io.PrintWriter;

import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;

import com.itextpdf.awt.geom.Dimension;

public class Linewrap extends Extension {

    public Linewrap() {
        super("extensions/linewrap.ps");
    }

    @Override
    public boolean zustaendig(Element elem) {
        return elem instanceof toni.druck.elements.TextArea;
    }

    // Ausgabe für Postscript
    @Override
    public void print(Element elem, PrintWriter out) {
        Dimension apos = getAbsolutStartPosition(elem);

        out.print(" LG " + apos.width / 10 + " cm  ");
        out.print(" " + apos.height / 10 + " cm moveto ");

        out.print(" " + apos.width / 10 + " cm LM ");
        out.print(" " + apos.height / 10 + " cm BM ");
        out.print(" " + (apos.width + elem.getWidth()) / 10 + " cm RM  ");
        out.print(" " + apos.height / 10 + " cm TM ");
        out.print(" L (");
        out.print(elem.getText());
        out.println(") P ");
    }

    private Dimension getAbsolutStartPosition(Element e) {
        Page p = e.getPage();
        int w = e.X();
        int h = e.Y();
        if (h > p.getHeight()) {
            throw new RuntimeException("Seite zu klein " + e.getName());
        }
        return new Dimension(w, p.getHeight() - h);
    }
}
