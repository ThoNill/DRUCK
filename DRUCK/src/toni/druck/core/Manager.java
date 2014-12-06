package toni.druck.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.jdom2.Element;

import toni.druck.filter.BasisFilter;
import toni.druck.filter.Filter;
import toni.druck.filter.FilterFactory;
import toni.druck.filter.FilterGroup;
import toni.druck.page.DataItem;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;

public class Manager extends BasisFilter {

	private Reader reader = null;
	private DataFIFO datenQueue;
	private Page page;
	private FilterGroup filterGroup = null;

	private PageRenderer renderer;

	public Manager(PageLoader loader, PageRenderer renderer) {
		this.renderer = renderer;
		datenQueue = new DataFIFO(10000, loader);
	}

	public void print(String dataFileName) {
		try {
			print(new FileReader(dataFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void print(java.io.Reader input) {
		try {
			reader = new Reader(datenQueue);
			reader.read(input);

			do {
				DataItem item;
				item = datenQueue.take();
				getFilter().receive(item);

			} while (!reader.isEmpty());

			finishPage();
			renderer.endDocument();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean doInclude(DataItem d) {
		boolean ok = DataItem.INCLUDE.equals(d.getCommand());
		if (ok) {
			try {
				renderer.include(d.getData(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}

	private void finishPage() {
		if (page != null) {
			page.finish(renderer);
			page = null;
		}
	}

	private boolean doLoadLayout(DataItem d) {
		boolean ok = DataItem.LAYOUT.equals(d.getCommand());
		if (ok) {
			if (page != null) {
				transferData(d, page, d.getPage());
				page.printNewPage(renderer);
			}
			finishPage();
			setPage(d);
		}
		return ok;
	}

	protected void transferData(DataItem d, Page oldPage, Page newPage) {
		if (d == null || oldPage == null || newPage == null)
			return;

		int size = d.getSize();
		for (int pos = 2; pos < size; pos++) {
			String name = d.getData(pos);
			newPage.putData(name, oldPage.getData(name));
		}
	}

	private void setPage(DataItem d) {
		page = d.getPage();
		Vector<Element> filterElements = page.getFilterElements();
		if (filterElements != null) {
			filterGroup = FilterFactory.getFilterGroup(filterElements);
			filterGroup.addFollower(this);
		}
	}

	private boolean doOutput(DataItem d) {
		boolean ok = DataItem.OUTPUT.equals(d.getCommand());
		if (ok) {
			try {
				renderer.setOutput(d.getData(1));

			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}

		}
		return ok;
	}

	private boolean doPrint(DataItem d) {
		boolean ok = DataItem.PRINT.equals(d.getCommand())
				|| DataItem.ENDOFFILE.equals(d.getCommand());
		if (ok) {
			page.printNewPage(renderer);
		}
		;
		return ok;
	}

	private boolean doData(DataItem d) {
		page = d.getPage();
		if (page != null) {
			page.printSectionOfTheItem(d, renderer);
		}
		return true;
	}

	@Override
	public void receive(DataItem item) {
		if (item != null
				&& (doLoadLayout(item) || doOutput(item) || doPrint(item)
						|| doInclude(item) || doData(item))) {

		}
	}

	private Filter getFilter() {
		if (filterGroup == null) {
			return this;
		}
		return filterGroup;
	}

}
