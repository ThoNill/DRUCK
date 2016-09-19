package tester;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.xml.XMLPageLoader;

public class HeaderAndFooterTest {
    private static final Logger LOG = Logger.getLogger(HeaderAndFooterTest.class.getName());

	
	public void loadTest(int nr) {
		try {
			PageLoader l = new XMLPageLoader();
			PageRenderer r = new PostscriptRenderer("ISO-8859-1");
			Manager m = new Manager(l, r);
			m.print("testdaten/testdaten" + nr + ".txt");
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void load7() {
		loadTest(7);
	}

	

}
