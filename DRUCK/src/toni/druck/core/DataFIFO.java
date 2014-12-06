package toni.druck.core;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import toni.druck.page.DataItem;
import toni.druck.page.Element;
import toni.druck.page.Page;

public class DataFIFO { 

	private static final long serialVersionUID = 1L;
	UnprotectedArrayQueue<DataItem> withSections = null;
	UnprotectedArrayQueue<DataItem> dataItems;
	Page page;
	private PageLoader loader;

	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();
	int capacity;
	int count = 0;
	boolean fuellen = true;

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
				fuellen = false;
			}
			dataItems.insert(item);
			count++;
			doLoad(item);
			item.setPage(page);
			if (page != null) {
				if (item.hasSection()) {
					withSections.insert(item);
				}
			}
			notEmpty.signal();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();
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
				if (item != null && !withSections.isEmpty()) {
					item.setNextItemOfTheSameType(false);
					DataItem sitem = withSections.peek();
					if (sitem == item) {
						sitem = withSections.extract();
						item.setNextItemOfTheSameType(isNextItemOfTheSameType(item));
					}
				}
			}
			notFull.signal();
			return item;
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

	public int getCountOfItemsWithElements() {
		lock.lock();
		try {
			return withSections.size();
		} finally {
			lock.unlock();
		}
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
