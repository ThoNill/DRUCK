package toni.druck.core;

import toni.druck.page.Page;

/**
 * 
 * @author Thomas Nill
 * 
 * Fabrik f�r Page, holt Layouts  
 * 
 */
public interface PageLoader {
	Page createPage(String name);
}
