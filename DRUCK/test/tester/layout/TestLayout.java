package tester.layout;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import toni.druck.core.StandardElement;
import toni.druck.core.VHElement;
import toni.druck.core2.Page;
import toni.druck.elements.Hbox;
import toni.druck.elements.Vbox;
import toni.druck.xml.XMLPageLoader;

public class TestLayout {
	int nr = 0;

	public TestLayout() {

	}

	private int nextNr() {
		nr++;
		return nr;
	}

	private String nextName() {
		return "n" + nextNr();
	}

	private StandardElement createStandardElement(VHElement parent, int height,
			int width, boolean absolute) {
		StandardElement s = new StandardElement(nextName(), parent.getPage());
		setDimensions(s, height, width, absolute);
		parent.addChild(s);
		return s;
	}

	private VHElement createVbox(Page page, int height, int width,
			boolean absolute) {
		VHElement s = new Vbox(nextName(), page);
		setDimensions(s, height, width, absolute);
		page.addSection(s);
		return s;
	}
	
	private VHElement createHbox(Page page, int height, int width,
			boolean absolute) {
		VHElement s = new Hbox(nextName(), page);
		setDimensions(s, height, width, absolute);
		page.addSection(s);
		return s;
	}
	
	private VHElement createVbox(VHElement parent, int height, int width,
			boolean absolute) {
		VHElement s = new Vbox(nextName(), parent.getPage());
		setDimensions(s, height, width, absolute);
		parent.addChild(s);
		return s;
	}
	
	private VHElement createHbox(VHElement parent, int height, int width,
			boolean absolute) {
		VHElement s = new Hbox(nextName(), parent.getPage());
		setDimensions(s, height, width, absolute);
		parent.addChild(s);
		return s;
	}

	private void setDimensions(StandardElement s, int height, int width,
			boolean absolute) {
		if (height >= 0) {
			s.setHeight(height);
		}
		if (width >= 0) {
			s.setWidth(width);
		}
		s.setAbsolute(absolute);
	}
	
	

	@Test
	public void layoutHBox() {
		try {
			Page page = new Page("test");
			
			VHElement elem = createHbox(page, -1, -1,false);
			
			StandardElement s1 = createStandardElement(elem, 7,13,false);
			StandardElement s2 = createStandardElement(elem, 7,13,false);
			StandardElement s3 = createStandardElement(elem, 7,13,false);
			
			
			page.layout();
			
			assertEquals(3 * 13,elem.getWidth());
			assertEquals(7,elem.getHeight());
			
			assertEquals(0,s1.getRelX());
			assertEquals(13,s2.getRelX());
			assertEquals(2*13,s3.getRelX());
			
			assertEquals(0,s1.getRelY());
			assertEquals(0,s2.getRelY());
			assertEquals(0,s3.getRelY());
			
			

		} catch (Exception e) {
			fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void layoutVBox() {
		try {
			Page page = new Page("test");
			VHElement elem = createVbox(page, -1, -1,false);
			
			StandardElement s1 = createStandardElement(elem, 7,13,false);
			StandardElement s2 = createStandardElement(elem, 7,13,false);
			StandardElement s3 = createStandardElement(elem, 7,13,false);
			
			page.layout();
			
			assertEquals(13,elem.getWidth());
			assertEquals(3 *7,elem.getHeight());
			
			assertEquals(0,s1.getRelX());
			assertEquals(0,s2.getRelX());
			assertEquals(0,s3.getRelX());
			
			assertEquals(0,s1.getRelY());
			assertEquals(7,s2.getRelY());
			assertEquals(2 * 7,s3.getRelY());
			
			

		} catch (Exception e) {
			fail("Exception " + e.getMessage());
		}
	}

	
	
	public void layoutVerschachtelt(boolean absolut) {
		try {
			Page page = new Page("test");
			VHElement horiz = createHbox(page, -1, -1,false);
			
			VHElement vert1 = createVbox(horiz, -1, -1,false);
			
			StandardElement s11 = createStandardElement(vert1, 7,13,absolut);
			StandardElement s12 = createStandardElement(vert1, 7,13,absolut);
			StandardElement s13 = createStandardElement(vert1, 7,13,absolut);
			
			VHElement vert2 = createVbox(horiz, -1, -1,absolut);
	
			StandardElement s21 = createStandardElement(vert2, 7,13,absolut);
			StandardElement s22 = createStandardElement(vert2, 7,13,absolut);
			StandardElement s23 = createStandardElement(vert2, 7,13,absolut);
		
			
			page.layout();
			
			assertEquals(2 *13,horiz.getWidth());
			assertEquals(3 *7,horiz.getHeight());
			
			assertEquals(13,vert1.getWidth());
			assertEquals(3 *7,vert1.getHeight());
						
			assertEquals(13,vert2.getWidth());
			assertEquals(3 *7,vert2.getHeight());			
			
			assertEquals(0,s11.X());
			assertEquals(0,s12.X());
			assertEquals(0,s13.X());
			assertEquals(0,s11.Y());
			assertEquals(7,s12.Y());
			assertEquals(2 * 7,s13.Y());
		
			
			assertEquals(13,s21.X());
			assertEquals(13,s22.X());
			assertEquals(13,s23.X());
			assertEquals(0,s21.Y());
			assertEquals(7,s22.Y());
			assertEquals(2 * 7,s23.Y());
				
	
		} catch (Exception e) {
			fail("Exception " + e.getMessage());
		}
	}

	@Test
	public void layoutVerschachtelt() {
		layoutVerschachtelt(false);
		layoutVerschachtelt(true);
	}

}
