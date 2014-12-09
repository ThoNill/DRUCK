package toni.druck.filter;

import toni.druck.page.DataItem;

/**
 * 
 * @author Thomas Nill
 * 
 * Filter die in einen Datenstrom von DataItem 
 * diesen Datenstrom modifizieren können
 *  * 
 */
public interface Filter {

	void receive(DataItem item);

	void addFollower(Filter filter);

	void removeFollower(Filter filter);

}