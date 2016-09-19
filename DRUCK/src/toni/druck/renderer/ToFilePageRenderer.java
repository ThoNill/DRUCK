package toni.druck.renderer;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import toni.druck.chart.Chart;
import toni.druck.helper.DirectoryHelper;
import toni.druck.page.Element;
import toni.druck.page.Extension;
import toni.druck.page.Page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Ausgabe der Daten eines Ausdruckes in einer Datei zu Debugzwecken
 * 
 *         Datenstruktur
 * 
 */
public class ToFilePageRenderer extends DefaultPageRenderer {
    private static final Logger LOG = Logger.getLogger(ToFilePageRenderer.class
            .getName());

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
    }

    public void printDefs(Element elem) {
    }

    public void setOutput(OutputStream out) {
        try {
            this.out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(out, getCodepage()), 1000));
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }
    }

    public void setOutput(String name) throws FileNotFoundException {
        try {
            DirectoryHelper.createDirsForFile(name);
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
    }

    public void include(String filename) throws IOException {
    }

    @Override
    protected void printChart(Chart elem, JFreeChart chart) {
    }
}
