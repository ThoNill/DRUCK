package tester;

import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core2.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.renderer.ToFilePageRenderer;
import toni.druck.xml.XMLPageLoader;

public class FullTest0 {

	@Test
	public void testClone() {
		TestPageLoader l = new TestPageLoader();
		TestRenderer r = new TestRenderer();
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten1.txt");
		r.print();
	}

	@Test
	public void testPostscript() {
		TestPageLoader l = new TestPageLoader();
		TestRenderer r = new TestRenderer();
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten1.txt");
		r.print();
	}

	@Test
	public void testSummen() {
		TestPageLoader l = new TestPageLoader();
		TestRenderer r = new TestRenderer();
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten2.txt");
		r.print();
	}

	@Test
	public void testRenderer() {
		XMLPageLoader l = new XMLPageLoader();
		TestRenderer r = new TestRenderer();
		Manager m = new Manager(l, r);
		m.print("testdaten/testdaten8.txt");
		r.print();
	}
	
}
