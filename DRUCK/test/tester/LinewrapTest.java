package tester;

import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.xml.XMLPageLoader;
import extensions.Linewrap;

public class LinewrapTest {

	@Test
	public void testClone() {
		PageLoader l = new XMLPageLoader();
		PageRenderer r = new PostscriptRenderer("ISO-8859-1");
		r.addExtension(new Linewrap());
		Manager m = new Manager(l, r);
		
		m.print("testdaten/linewrap.txt");
	}
}
