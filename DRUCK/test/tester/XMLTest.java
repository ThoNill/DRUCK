package tester;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.main.Main;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.testdatei.ErzeugeTestBescheibung;
import toni.druck.xml.DruckWalker;
import toni.druck.xml.EditorXMLWalker;
import toni.druck.xml.XMLPageLoader;
import extensions.Haken;
import extensions.Omr;
import extensions.QRCode;

public class XMLTest {
    private static final String DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG = "Diese Exception ist nicht in Ordnung";
    private static final Logger LOG = Logger.getLogger(XMLTest.class.getSimpleName());

	@Test
	public void loadDocument() {
		try {
		    Page page = new XMLPageLoader().createPage("testdaten/test1.xml");
			assertNotNull(page);
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void loadDocument2() {
		try {
			 Page page =new XMLPageLoader().createPage("testdaten/test1");
			assertNotNull(page);
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}


	@Test
	public void loadAndBearbeiteDocument() {
		try {
			Document doc = new XMLPageLoader().createDocument("testdaten/test1.xml");
			DruckWalker walker = new DruckWalker();
			walker.walkAlong(doc);
			Page page = walker.getPage();
			Assert.assertEquals("erster", page.getName());
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void loadAndBearbeiteDocument2() {
		try {
			Document doc = new XMLPageLoader().createDocument("testdaten/test2.xml");
			DruckWalker walker = new DruckWalker();
			walker.walkAlong(doc);
			Page page = walker.getPage();
			Assert.assertEquals("Abrechnung", page.getName());
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void loadAndPrintDocument2() {
		try {
			PageLoader l = new XMLPageLoader();
			PageRenderer r = new PostscriptRenderer("ISO-8859-1");
			r.addExtension(new QRCode());
			Manager m = new Manager(l, r);
			m.print("testdaten/testdaten4.txt");
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void loadAndPrintDocument3() {
		try {
			PageLoader l = new XMLPageLoader();
			PageRenderer r = new PostscriptRenderer("ISO-8859-1");
			r.addExtension(new QRCode());
			Manager m = new Manager(l, r);
			m.print("testdaten/testdaten5.txt");
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
		    Assert.fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void loadAndPrintDocument4() {
		try {
			PageLoader l = new XMLPageLoader();
			PageRenderer r = new PostscriptRenderer("ISO-8859-1");
			r.addExtension(new QRCode());
			Manager m = new Manager(l, r);
			m.print("testdaten/testdaten6.txt");
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}


	@Test
	public void createTestFile() {
		try {
			PageLoader l = new XMLPageLoader();
			PageRenderer r = new PostscriptRenderer("ISO-8859-1");
			r.addExtension(new QRCode());
			r.addExtension(new Omr());
			r.addExtension(new Haken());
			Manager m = new Manager(l, r);
			long start = System.currentTimeMillis();
			m.print("testdaten/testbiglist.txt");
			long end = System.currentTimeMillis();
			System.out.println(end-start);
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void createXMLFile() {
		try {
			
			Document doc = new XMLPageLoader().createDocument("testdaten/biglist.xml");
			EditorXMLWalker walker = new EditorXMLWalker();
			walker.walkAlong(doc);
			walker.printXML("results/biglist_gui.xml");
		
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void createTestDescription() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/biglist");
			ErzeugeTestBescheibung erz = new ErzeugeTestBescheibung();
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void probedruck() {
		try {
			Main.probedruck("testdaten/biglist","results/","testdaten/");
		} catch (Exception e) {
		    LOG.error(DIESE_EXCEPTION_IST_NICHT_IN_ORDNUNG, e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
}
