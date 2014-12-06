package tester;

import junit.framework.Assert;

import org.junit.Test;

import toni.druck.core.DataFIFO;
import toni.druck.core.PageLoader;
import toni.druck.page.DataItem;
import toni.druck.page.Element;
import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;


public class FIFO {

	
	 @Test
	 public void create() {
		 Page p = new Page("test");
		 Element s = new StandardElement("first", p);
		 p.addSection(s);
		 PageLoader l = new SinglePageLoader(p);
		 new DataFIFO(100,l);
	 }
	 
 
	 @Test
	 public void pollIfEmpty() throws InterruptedException {
		 DataFIFO fifo = createFIFO();
		 endOfData(fifo);
		 fifo.take();
	 }


	private void endOfData(DataFIFO fifo) throws InterruptedException {
		fifo.offer(new DataItem(DataItem.ENDOFFILE));
	}
	 
	 @Test
	 public void offer() throws InterruptedException {
		 DataFIFO fifo = createFIFO();
		 DataItem item = new DataItem("first1|name");
		 fifo.offer(item);
		 Assert.assertEquals(false,item.hasSection());
		 Assert.assertEquals(0, fifo.getCountOfItemsWithElements());
		 Assert.assertEquals(1, fifo.size());
	 }

	private DataFIFO createFIFO() throws InterruptedException {
		 Page p = new Page("test");
		 Element s = new StandardElement("first", p);
		 p.addSection(s);
		 PageLoader l = new SinglePageLoader(p);
		 DataFIFO fifo = new DataFIFO(100,l);
		 DataItem item = new DataItem(DataItem.LAYOUT + "|first");
		 fifo.offer(item); // Damit Page geladen wird
		 endOfData(fifo);
		 fifo.take();
		 fifo.take();
		 return fifo;
	}
	 
	 @Test
	 public void offerPollk() throws InterruptedException {
		 DataFIFO fifo= createFIFO();
		 DataItem item = new DataItem("first|name");
		 fifo.offer(item);
		 fifo.take();
	 }
	 
	 @Test
	 public void offer2() throws InterruptedException {
		
		 
		 DataFIFO fifo= createFIFO();
		 
		 DataItem item = new DataItem("first|name");
		 fifo.offer(item);
		 Assert.assertEquals(true,item.hasSection());
		 Assert.assertEquals(1, fifo.getCountOfItemsWithElements());
	 }
	 
	 @Test
	 public void offerPoll2() throws InterruptedException {
		 DataFIFO fifo= createFIFO();
		
		 DataItem item = new DataItem("first|name");
		 fifo.offer(item);
		 fifo.take();
		 Assert.assertEquals(0, fifo.getCountOfItemsWithElements());
		 Assert.assertEquals(0, fifo.size());
	 }
	 
	 @Test
	 public void offerPoll3() throws InterruptedException {
		 DataFIFO fifo= createFIFO();
		
		 DataItem item = new DataItem("first|name");
		 fifo.offer(item);
		 fifo.offer(item = new DataItem("second|name"));
		 fifo.take();
		 Assert.assertEquals(0, fifo.getCountOfItemsWithElements());
		 Assert.assertEquals(1, fifo.size());
	 }
	 
	 
	 @Test
	 public void offerPoll4() throws InterruptedException {
	 
		 DataFIFO fifo= createFIFO();
		
		 DataItem item = new DataItem("second|name");
		 fifo.offer(item);
		 fifo.offer(item = new DataItem("first|name"));
		 fifo.take();
		 Assert.assertEquals(1, fifo.getCountOfItemsWithElements());
		 Assert.assertEquals(1, fifo.size());
	 }
	 
	 
}
