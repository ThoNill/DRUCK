package tester.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Element;
import org.junit.Assert;
import org.junit.Test;
import toni.druck.core.PageLoader;
import toni.druck.filter.AppendFilter;
import toni.druck.filter.Filter;
import toni.druck.filter.FilterFactory;
import toni.druck.filter.FilterGroup;
import toni.druck.page.Page;
import toni.druck.xml.XMLPageLoader;

public class PageWithFilterTest {
    private static final Logger LOG = Logger.getLogger(PageWithFilterTest.class.getName());



	@Test
	public void createTestFile() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/filter");
			
			List<Element> filterElements = page.getFilterElements();
			assertEquals(2,filterElements.size());
			if (!filterElements.isEmpty()) {
				Filter filter = FilterFactory.getFilter(filterElements.get(0));
				assertTrue(filter instanceof AppendFilter);
				assertEquals("start",((AppendFilter) filter).getCommand());
			}
		
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	
	@Test
	public void createTestFile2() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/filter");
			
			List<Element> filterElements = page.getFilterElements();
			assertEquals(2,filterElements.size());
			if (!filterElements.isEmpty()) {
				Filter filter = FilterFactory.getFilter(filterElements.get(1));
				assertNull(filter );
			}
		
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void filterFactory() {
		try {
			PageLoader l = new XMLPageLoader();
			Page page = l.createPage("testdaten/filter");
			
			List<Element> filterElements = page.getFilterElements();
			FilterGroup group = FilterFactory.getFilterGroup(filterElements);
			assertEquals(1,group.getFilterCount());
		
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}


}
