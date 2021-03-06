package toni.druck.page;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Erwiterung f�r den Ausdruck um neue Elemente
 * 
 *         Datentyp
 * 
 */
public class Extension {
    private String resname;

    public Extension(String resname) {
        super();
        this.resname = resname;
    }

    public static void readIn(PrintWriter out, String resname)
            throws IOException {
        InputStream in = new BufferedInputStream(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(resname));
        int c = in.read();
        while (c != -1) {
            out.write(c);
            c = in.read();
        }
    }

    public void extend(PrintWriter out) throws IOException {
        readIn(out, resname);
    }

    public boolean zustaendig(Element elem) {
        return false;
    }

    // Ausgabe f�r Postscript
    public void print(Element elem, PrintWriter out) {

    }

    // Ausgabe f�r PDF
    public void print(Element elem, Document document, PdfContentByte cb,
            PageRenderer renderer) {
    }

}
