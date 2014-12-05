package toni.druck.filter;

import toni.druck.core2.DataItem;

public interface Filter {

	void receive(DataItem item);

	void addFollower(Filter filter);

	void removeFollower(Filter filter);

}