package toni.druck.core;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import toni.druck.page.DataItem;
import toni.druck.page.Element;
import toni.druck.page.Page;


/**
 * 
 * @author Thomas Nill
 * 
 * Die aus einer Datendatei eingelesenen Daten werden zunächst in
 * einer Warteschlange zwischengespeichert, aus der die DataItem zur  
 * eigentliche Abarbeitung abgeholt werden.
 * 
 * Der Zwischenspeicher dient dazu, dass man auch auf nachfolgende 
 * Daten zugreifen kann. Dazu wird die Warteschlange zuerst bis
 * zur vollen Kapazität aufgefüllt, erst danach findet die eigentliche
 * Abarbeitung statt.
 * 
 * Damit in weiteren Verarbeitungsschritten Header und Footer richtig
 * gedruckt werden können, wird bestimmt, ob ein nachfolgendes DataItem 
 * den gleichen Abschnitt zu Ausdruck benutzt
 * 
 */
public class DataFIFO { 

	private static final long serialVersionUID = 1L;
	private UnprotectedArrayQueue<DataItem> withSections = null;
	private UnprotectedArrayQueue<DataItem> dataItems;
	private Page page;
	private PageLoader loader;
	private final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();
	private int capacity;
	private int count = 0;
	private boolean fuellen = true; // Gibt an, ob die Warteschlange voll gefüllt werden soll,
	

	public DataFIFO(int capacity, PageLoader loader) {
		super();
		this.loader = loader;
		withSections = new UnprotectedArrayQueue<DataItem>(capacity);
		dataItems = new UnprotectedArrayQueue<DataItem>(capacity);
		this.capacity = capacity;
	}

	public void offer(DataItem item) throws InterruptedException {
		lock.lock();
		try {
			while (count == capacity)
				notFull.await();
			if (DataItem.ENDOFFILE.equals(item.getCommand())) {
				// Wenn es keine Daten mehr gibt, nicht mehr füllen.
				fuellen = false;
			}
			dataItems.insert(item);
			count++;
			doLoad(item);
			item.setPage(page);
			fallsSectionDannMerken(item);
			notEmpty.signal();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	private void fallsSectionDannMerken(DataItem item) {
		if (page != null) {
			if (item.hasSection()) {
				withSections.insert(item);
			}
		}
	}

	public DataItem take() throws InterruptedException {
		lock.lock();
		try {
			while (fuellen && count < capacity - 1)
				notEmpty.await();
			DataItem item = null;
			if (count > 0) {
				item = dataItems.extract();
				count--;
				beachteNachfolgendeSections(item);
			}
			notFull.signal();
			return item;
		} finally {
			lock.unlock();
		}
	}

	private void beachteNachfolgendeSections(DataItem item) {
		item.setNextItemOfTheSameType(false);
		if (item != null && !withSections.isEmpty()) {
			DataItem sitem = withSections.peek();
			if (sitem == item) {
				sitem = withSections.extract();
				item.setNextItemOfTheSameType(isNextItemOfTheSameType(item));
			}
		}
	}

	// Nur für Testzwecke
	public int getCountOfItemsWithSections() {
		lock.lock();
		try {
			return withSections.size();
		} finally {
			lock.unlock();
		}
	}

	private boolean isNextItemOfTheSameType(DataItem item) {
		Element section = item.getSection();
		if (section != null && !withSections.isEmpty()) {
			Element e = withSections.peek().getSection();
			return section.getName().equals(e.getName());
		}
		return false;
	}


	private void doLoad(DataItem d) {
		boolean ok = DataItem.LAYOUT.equals(d.getCommand());
		if (ok) {
			page = loader.createPage(d.getData(1));
		}
	}

	public boolean isEmpty() {
		lock.lock();
		try {
			return dataItems.isEmpty();
		} finally {
			lock.unlock();
		}

	}

	public Object size() {
		lock.lock();
		try {
			return dataItems.size();
		} finally {
			lock.unlock();
		}
	}

}
