package tester;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.xml.XMLPageLoader;
import extensions.Haken;
import extensions.Omr;
import extensions.QRCode;

public class BiglistTest {
    private static final Logger LOG = Logger.getLogger(BiglistTest.class.getName());
   


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
			m.print("testdaten/testbiglist2.txt");
			long end = System.currentTimeMillis();
			System.out.println(end-start);
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	


}
