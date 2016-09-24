package tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.page.Element;
import toni.druck.page.Page;
import toni.druck.xml.XMLPageLoader;

public class XMLIncludeTest {
    private static final Logger LOG = Logger.getLogger(XMLIncludeTest.class
            .getSimpleName());

	@Test
	public void loadDocument1() {
		try {
			new XMLPageLoader().createPage("testdaten/testtemplates.xml");
		} catch (Exception e) {
		    LOG.error("echter Fehler",e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	
	
	@Test
	public void fehlt() {
		try {
			Page page = new XMLPageLoader().createPage("testdaten/testtemplates1.xml");
			assertNull(page);
		} catch (Exception e) {
		    LOG.error("kein echter Fehler",e);
		}
	}


	@Test
	public void loadDocument2() {
		try {
			Page page = new XMLPageLoader().createPage("testdaten/testtemplates.xml");
			Element elem = page.getSection("include");
			assertNull(elem);
			elem = page.getSection("footer");
			assertNotNull(elem);
			assertEquals(20,elem.getLinewidth());
		} catch (Exception e) {
		    LOG.error("echter Fehler",e);
			Assert.fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void loadDocument3() {
		try {
			Page page = new XMLPageLoader().createPage("testdaten/testtemplates.xml");
			Element elem = page.getSection("gehtVerloren");
			assertNull(elem);
			elem = page.getSection("kommtDazu");
			assertNotNull(elem);
		} catch (Exception e) {
		    LOG.error("echter Fehler",e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
}
