package toni.druck.core;

import toni.druck.page.Page;

public interface PageLoader {
	Page createPage(String name);
}
