package tester;

import org.junit.Assert;
import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.core2.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.xml.XMLPageLoader;
import extensions.Haken;
import extensions.Omr;
import extensions.QRCode;

public class BiglistTest {


	@Test
	public void createTestFile() {
		try {
	/*		TestDateiCreator creator = new TestDateiCreator();
			 creator.loadAndWrite("biglist");
*/
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
/*		do {
				m.print("testbiglist.txt");
				Thread.sleep(100);
			} while (1 == 1);*/
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}
	


}
