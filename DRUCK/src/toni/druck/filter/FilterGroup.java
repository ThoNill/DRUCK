package toni.druck.filter;

import java.util.Vector;

import toni.druck.page.DataItem;


/**
 * 
 * @author Thomas Nill
 * 
 * Erzeugt eine Gruppe von Filtern die hintereinander abgearbeitet werden
 * @see Filter
 * 
 * @see BasisFilter 
 *  
 */

public class FilterGroup implements Filter {
	private Vector<Filter> filters = new Vector<Filter>();

	public FilterGroup() {
		super();
	}

	public int getFilterCount() {
		return filters.size();
	}

	private Filter getFirst() {

		if (filters.size() > 0) {
			return filters.get(0);
		}
		return null;
	}

	private Filter getLast() {
		if (filters.size() > 0) {
			return filters.get(filters.size() - 1);
		}
		return null;
	}

	private Filter getBeforeLast() {
		if (filters.size() > 1) {
			return filters.get(filters.size() - 2);
		}
		return null;
	}

	public void receive(DataItem item) {
		Filter f = getFirst();
		if (f != null) {
			f.receive(item);
		}
	}

	public void testAllreadyThere(Filter filter) {
		if (filters.contains(filter)) {
			throw new RuntimeException("Filter is allready in FilterGroup");
		}
	}

	public void addFollower(Filter filter) {
		testAllreadyThere(filter);
		Filter f = getLast();
		if (f != null) {
			f.addFollower(filter);
		}
		filters.add(filter);
	}

	public void removeFollower(Filter filter) {
		int index = filters.indexOf(filter);
		if (index >= 0) {
			Filter next = (index + 1 < filters.size()) ? filters.get(index + 1)
					: null;
			Filter prev = (index > 0) ? filters.get(index - 1) : null;
			if (prev != null) {
				prev.removeFollower(filter);
				if (next != null) {
					prev.addFollower(next);
				}
			}
			filters.remove(filter);
		}

	}

}
