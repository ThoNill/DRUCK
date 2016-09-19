package tester;



import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.chart.BalkenFactory;
import toni.druck.chart.Chart;
import toni.druck.core.Manager;
import toni.druck.core.PageLoader;
import toni.druck.page.PageRenderer;
import toni.druck.renderer.PDFRenderer;
import toni.druck.renderer.PostscriptRenderer;
import toni.druck.xml.XMLPageLoader;
import extensions.Haken;
import extensions.Omr;
import extensions.QRCode;

public class TesteBalkenFactory {
    private static final Logger LOG = Logger.getLogger(TesteBalkenFactory.class.getName());

   
	
		@Test
		public void teste1() {
			BalkenFactory bf = new BalkenFactory();
			Chart chart = new Chart();
			String values[] = new String[] { "1#1.0#2#3.4"};
			DefaultCategoryDataset dataset = bf.fülleDasDataSet(chart, values);
			Assert.assertEquals(1,dataset.getRowCount());
			Assert.assertEquals(2,dataset.getColumnCount());
			Assert.assertEquals(1.0,dataset.getValue(0,0));
			Assert.assertEquals(3.4,dataset.getValue(0,1));
		}
		
		@Test
		public void teste2() {
			BalkenFactory bf = new BalkenFactory();
			Chart chart = new Chart();
			String values[] = new String[] { "1#1.0#3#3.4"};
			DefaultCategoryDataset dataset = bf.fülleDasDataSet(chart, values);
			Assert.assertEquals(1,dataset.getRowCount());
			Assert.assertEquals(2,dataset.getColumnCount());
			Assert.assertEquals(1.0,dataset.getValue(0,0));
			Assert.assertEquals(3.4,dataset.getValue(0,1));
		}
		
		@Test
		public void teste3() {
			BalkenFactory bf = new BalkenFactory();
			bf.setFehlendeWerteFuellen(true);
			Chart chart = new Chart();
			String values[] = new String[] { "1#1.0#3#3.4"};
			DefaultCategoryDataset dataset = bf.fülleDasDataSet(chart, values);
			Assert.assertEquals(1,dataset.getRowCount());
			Assert.assertEquals(3,dataset.getColumnCount());
			Assert.assertEquals(1.0,dataset.getValue(0,0));
			Assert.assertEquals(0.0,dataset.getValue(0,1));
			Assert.assertEquals(3.4,dataset.getValue(0,2));
		}
		
		
		@Test
		public void teste4() {
			BalkenFactory bf = new BalkenFactory();
			bf.setFehlendeWerteFuellen(true);
			Chart chart = new Chart();
			String values[] = new String[] { "1#1.0#5#3.4"};
			DefaultCategoryDataset dataset = bf.fülleDasDataSet(chart, values);
			Assert.assertEquals(1,dataset.getRowCount());
			Assert.assertEquals(5,dataset.getColumnCount());
			
			Assert.assertEquals(1.0,dataset.getValue(0,0));
			Assert.assertEquals(0.0,dataset.getValue(0,1));
			Assert.assertEquals(0.0,dataset.getValue(0,2));
			Assert.assertEquals(0.0,dataset.getValue(0,3));
			Assert.assertEquals(3.4,dataset.getValue(0,4));
		}
		
		@Test
		public void teste5() {
			BalkenFactory bf = new BalkenFactory();
			bf.setFehlendeWerteFuellen(true);
			Chart chart = new Chart();
			String values[] = new String[] { "1#1.0#5#3.4","1#1.0#2#3.4"};
			DefaultCategoryDataset dataset = bf.fülleDasDataSet(chart, values);
			Assert.assertEquals(2,dataset.getRowCount());
			Assert.assertEquals(5,dataset.getColumnCount());
			
			Assert.assertEquals(1.0,dataset.getValue(0,0));
			Assert.assertEquals(0.0,dataset.getValue(0,1));
			Assert.assertEquals(0.0,dataset.getValue(0,2));
			Assert.assertEquals(0.0,dataset.getValue(0,3));
			Assert.assertEquals(3.4,dataset.getValue(0,4));
		}
		
		@Test
		public void teste6() {
			BalkenFactory bf = new BalkenFactory();
			bf.setFehlendeWerteFuellen(true);
			bf.setGroupSize(3);
			Chart chart = new Chart();
			String values[] = new String[] { "1#1.0#eins#5#3.4#fünf","1#1.0#eins#2#3.4#zwei"};
			DefaultCategoryDataset dataset = bf.fülleDasDataSet(chart, values);
			Assert.assertEquals(2,dataset.getRowCount());
			Assert.assertEquals(6,dataset.getColumnCount());
			
			Assert.assertEquals(1.0,dataset.getValue(0,0));
			Assert.assertEquals(0.0,dataset.getValue(0,1));
			Assert.assertEquals(0.0,dataset.getValue(0,2));
			Assert.assertEquals(0.0,dataset.getValue(0,3));
			Assert.assertEquals(3.4,dataset.getValue(0,4));
		}
		
		@Test
		public void loadAndPrintDocument5() {
			try {
				PageLoader l = new XMLPageLoader();
				PageRenderer r = new PostscriptRenderer("ISO-8859-1");
				r.addExtension(new QRCode());
				r.addExtension(new Omr());
				r.addExtension(new Haken());
				Manager m = new Manager(l, r);
				m.print("testdaten/biglist.txt");
			} catch (Exception e) {
				LOG.error(e);
				Assert.fail("Exception " + e.getMessage());
			}
		}
		
		@Test
		public void loadAndPrintDocument5PDF() {
			try {
				PageLoader l = new XMLPageLoader();
				PageRenderer r = new PDFRenderer();
				r.addExtension(new QRCode());
				r.addExtension(new Omr());
				r.addExtension(new Haken());
				Manager m = new Manager(l, r);
				m.print("testdaten/biglistpdf.txt");
			} catch (Exception e) {
				LOG.error(e);
				Assert.fail("Exception " + e.getMessage());
			}
		}

}
