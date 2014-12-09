package toni.druck.filter;

import java.util.Vector;

import toni.druck.page.DataItem;

/**
 * 
 * @author Thomas Nill
 * 
 * Basisimplementierung von Filter.
 * 
 * Die Nachfolger sind gleichberechtigt und das DataItem,
 * das im Filter ankommt, bzw von diesem erzeugt wird , wird parallel
 * an alle Nachfolger weitergegeben.
 * 
 * 
 */

public abstract class BasisFilter implements Filter {
	private Vector<Filter> followers = new Vector<Filter>();

	abstract public void receive(DataItem item);

	public void addFollower(Filter filter) {
		testAllreadyThere(filter);
		followers.addElement(filter);
	}

	public void removeFollower(Filter filter) {
		followers.removeElement(filter);
	}

	protected void send(DataItem item) {
		for (Filter f : followers) {
			f.receive(item);
		}
	}

	public void testAllreadyThere(Filter filter) {
		if (followers.contains(filter)) {
			throw new RuntimeException("Filter is allready in BasisFilter");
		}
	}

}
