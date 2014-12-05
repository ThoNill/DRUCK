package tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;

import toni.druck.core2.Element;
import toni.druck.core2.Page;
import toni.druck.xml.XMLPageLoader;

public class XMLIncludeTest {

	@Test
	public void loadDocument1() {
		try {
			new XMLPageLoader().createPage("testdaten/testtemplates.xml");
		} catch (Exception e) {
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void fehlt() {
		try {
			Page page = new XMLPageLoader().createPage("testdaten/testtemplates1.xml");
			assertNull(page);
		} catch (Exception e) {
			Assert.fail("Exception " + e.getMessage());
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
			Assert.fail("Exception " + e.getMessage());
		}
	}
}
