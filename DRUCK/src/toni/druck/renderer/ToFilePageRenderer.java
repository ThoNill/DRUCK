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

    @Override
    public void endDocument() {
        inPrint = false;
    }

    @Override
    public void print(Element elem) {
        if (out != null) {
            out.println(elem.getName());
        }
    }

    @Override
    public void printDefs(Element elem) {
    }

    @Override
    public void setOutput(OutputStream out) {
        try {
            this.out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(out, getCodepage()), 1000));
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }
    }

    @Override
    public void setOutput(String name) throws FileNotFoundException {
        try {
            DirectoryHelper.createDirsForFile(name);
            setOutput(new FileOutputStream(name));
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void startDocument(Page page) {
        if (inPrint) {
            endDocument();
        }
        inPrint = true;
    }

    @Override
    public int getStatus() {
        return ACCUMULATION;
    }

    @Override
    public void newPage(int pagenr, Page page) {
    }

    @Override
    public void addExtension(Extension qrCode) {
    }

    @Override
    public void include(String filename) throws IOException {
    }

    @Override
    protected void printChart(Chart elem, JFreeChart chart) {
    }
}
