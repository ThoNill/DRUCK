package toni.druck.filter;

import toni.druck.page.DataItem;

public interface Filter {

	void receive(DataItem item);

	void addFollower(Filter filter);

	void removeFollower(Filter filter);

}