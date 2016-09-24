package toni.druck.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.jdom2.Document;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PDFRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.testdatei.ErzeugeTestBescheibung;
import toni.druck.testdatei.TestDateiCreator;
import toni.druck.xml.EditorXMLWalker;
import toni.druck.xml.XMLPageLoader;
import extensions.Haken;
import extensions.Omr;
import extensions.QRCode;

/**
 * 
 * @author Thomas Nill
 * 
 *         Main Klasse
 * 
 * 
 */
public class Main {
    private static final Logger LOG = Logger.getLogger("Main");

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            usage();
            System.exit(1);
        }
        String command = args[0];
        String filename = args[1];

        try {
            if ("ErzTestdaten".equals(command)) {
                createTestdaten(filename, "", "");
            }
            ;
            if ("ErzPS".equals(command)) {
                createPS(filename);
            }
            if ("ErzPDF".equals(command)) {
                createPDF(filename);
            }
            if ("ErzGuiXML".equals(command)) {
                createGuiXML(filename);
            }
            if ("Probedruck".equals(command)) {
                probedruck(filename, "", "");
            }
        } catch (Exception e) {
            LOG.error(e);
        }

    }

    private static void createPDF(String filename) {

        PageLoader l = new XMLPageLoader();
        PageRenderer r = new PDFRenderer();
        r.addExtension(new QRCode());
        r.addExtension(new Omr());
        r.addExtension(new Haken());
        Manager m = new Manager(l, r);
        m.print(filename);
    }

    private static void createGuiXML(String filename) throws IOException  {

        XMLPageLoader l = new XMLPageLoader();
        Document doc = l.createDocument(filename);
        EditorXMLWalker walker = new EditorXMLWalker();
        walker.walkAlong(doc);
        walker.printXML(filename + "_gui.xml");
    }

    private static void usage() {
        System.out
                .println(" Übergabe: art dateiname \n art= ErzTestdaten, ErzPS ErzPDF ErzGuiXML Probedruck \n");
    }

    private static void createPS(String filename) throws IOException {
        createPS(new FileReader(filename));
    }

    private static void createPS(Reader in) throws IOException {
        PageLoader l = new XMLPageLoader();
        PageRenderer r = new PostscriptRenderer("ISO-8859-1");
        r.addExtension(new QRCode());
        r.addExtension(new Omr());
        r.addExtension(new Haken());
        Manager m = new Manager(l, r);
        m.print(in);
    }

    private static void createTestdaten(String filename, String outDir,
            String inDir) throws FileNotFoundException, IOException {
        TestDateiCreator creator = new TestDateiCreator(outDir, inDir);
        creator.loadAndWrite(filename);
    }

    public static void probedruck(String pagename, String outDir, String inDir)
            throws FileNotFoundException, IOException {
        XMLPageLoader loader = new XMLPageLoader();
        Page page = loader.createPage(pagename);
        byte[] aTestBeschreibung = erzeugeTestBeschreibung(page);

        byte[] aTestDaten = erzeugeTestDatei(page, aTestBeschreibung, outDir,
                inDir);

        createPS(new InputStreamReader(new ByteArrayInputStream(aTestDaten)));

    }

    private static byte[] erzeugeTestDatei(Page page, byte[] aTestBeschreibung,
            String outDir, String inDir) throws FileNotFoundException,
            IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(aTestBeschreibung);

        TestDateiCreator creator = new TestDateiCreator(outDir, inDir);
        ByteArrayOutputStream testDaten = new ByteArrayOutputStream();
        creator.loadAndWrite(page, in, testDaten);

        byte[] aTestDaten = testDaten.toByteArray();
        return aTestDaten;
    }

    private static byte[] erzeugeTestBeschreibung(Page page) throws IOException {
        ErzeugeTestBescheibung testErzeuger = new ErzeugeTestBescheibung();
        ByteArrayOutputStream testBescheibung = new ByteArrayOutputStream();
        testErzeuger.analysiereUndBeschreibe(page, new PrintWriter(
                new OutputStreamWriter(testBescheibung, "ISO-8859-1"), true));
        byte[] aTestBeschreibung = testBescheibung.toByteArray();
        return aTestBeschreibung;
    }
}
