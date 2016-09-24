package tester;

import toni.druck.core.PageLoader;
import toni.druck.page.Page;

public class SinglePageLoader implements PageLoader {
	Page page;
	
	public SinglePageLoader(Page page) {
		super();
		this.page = page;
	}

	@Override
    public Page createPage(String name) {
		return page;
	}

}
