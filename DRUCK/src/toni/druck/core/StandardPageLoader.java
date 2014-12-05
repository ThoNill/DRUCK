package toni.druck.core;

import java.util.HashMap;

import toni.druck.core2.Page;

public class StandardPageLoader implements PageLoader {
	HashMap<String, Page> pages;

	public StandardPageLoader() {
		pages = new HashMap<String, Page>();
	}

	public void add(Page page) {
		pages.put(page.getName(), page);
	}

	public Page createPage(String name) {
		return pages.get(name);
	}

}
