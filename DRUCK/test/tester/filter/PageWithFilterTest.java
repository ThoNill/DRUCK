package tester.filter;

import static org.junit.Assert.*;

import java.util.Vector;

import org.jdom2.Element;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.filter.AppendFilter;
import toni.druck.filter.Filter;
import toni.druck.filter.FilterFactory;
import toni.druck.filter.FilterGroup;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.xml.XMLPageLoader;
import extensions.Haken;
import extensions.Omr;
import extensions.QRCode;

public class PageWithFilterTest {


	@Test
	public void createTestFile() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/filter");
			
			Vector<Element> filterElements = page.getFilterElements();
			assertEquals(2,filterElements.size());
			if (filterElements != null) {
				Filter filter = FilterFactory.getFilter(filterElements.elementAt(0));
				assertTrue(filter instanceof AppendFilter);
				assertEquals("start",(((AppendFilter) filter).getCommand()));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	
	@Test
	public void createTestFile2() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/filter");
			
			Vector<Element> filterElements = page.getFilterElements();
			assertEquals(2,filterElements.size());
			if (filterElements != null) {
				Filter filter = FilterFactory.getFilter(filterElements.elementAt(1));
				assertNull(filter );
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void filterFactory() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/filter");
			
			Vector<Element> filterElements = page.getFilterElements();
			FilterGroup group = FilterFactory.getFilterGroup(filterElements);
			assertEquals(1,group.getFilterCount());
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}


}
