package tester;

import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.renderer.ToFilePageRenderer;
import toni.druck.xml.XMLPageLoader;

public class FullTest {

	@Test
	public void testClone() {
		TestPageLoader l = new TestPageLoader();
		PageRenderer r = new ToFilePageRenderer("ISO-8859-1");
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten1.txt");
	}

	@Test
	public void testPostscript() {
		TestPageLoader l = new TestPageLoader();
		PageRenderer r = new PostscriptRenderer("ISO-8859-1");
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten1.txt");
	}

	@Test
	public void testSummen() {
		TestPageLoader l = new TestPageLoader();
		PageRenderer r = new PostscriptRenderer("ISO-8859-1");
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten2.txt");
	}

	@Test
	public void testRenderer() {
		XMLPageLoader l = new XMLPageLoader();
		PageRenderer r = new PostscriptRenderer("ISO-8859-1");
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten8.txt");
	}
	
}
