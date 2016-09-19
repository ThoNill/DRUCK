package toni.druck.testdatei;

import java.io.IOException;
import java.io.Writer;

import toni.druck.page.Verteiler;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Erzeugen von Testdaten für einen {@link Verteiler}
 * 
 */
public class TestDateiVerteiler {
    private Verteiler verteiler;
    private int count;

    public TestDateiVerteiler(Verteiler verteiler, int count) {
        super();
        this.verteiler = verteiler;
        this.count = count;
    }

    public void printOneLine(Writer out) throws IOException {
        if (verteiler.getFields() != null) {
            out.write(verteiler.getName());
            String felder = verteiler.getFields().replace(",", "|:");
            out.write('|');
            out.write(felder);
            out.write("\n");
        }
    }

    public void printMultipleLines(Writer out, int count) throws IOException {
        if (verteiler.getFields() != null) {
            String felder[] = verteiler.getFields().split(" *, *");
            for (int r = 1; r <= count; r++) {
                out.write(verteiler.getName());
                for (int i = 0; i < felder.length; i++) {
                    out.write("|:");
                    out.write(felder[i]);
                    out.write("" + r);
                }
                out.write("\n");
            }
        }
    }

    public void print(Writer out) throws IOException {
        if (count > 1) {
            printMultipleLines(out, count);
        } else {
            printOneLine(out);
        }
    }
}
