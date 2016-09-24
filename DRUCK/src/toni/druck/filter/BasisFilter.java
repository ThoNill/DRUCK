package toni.druck.filter;

import java.util.ArrayList;
import java.util.List;

import toni.druck.page.DataItem;

/**
 * 
 * @author Thomas Nill
 * 
 *         Basisimplementierung von Filter.
 * 
 *         Die Nachfolger sind gleichberechtigt und das DataItem, das im Filter
 *         ankommt, bzw von diesem erzeugt wird , wird parallel an alle
 *         Nachfolger weitergegeben.
 * 
 * 
 */

public abstract class BasisFilter implements Filter {
    private List<Filter> followers = new ArrayList<Filter>();

    @Override
    abstract public void receive(DataItem item);

    @Override
    public void addFollower(Filter filter) {
        testAllreadyThere(filter);
        followers.add(filter);
    }

    @Override
    public void removeFollower(Filter filter) {
        followers.remove(filter);
    }

    protected void send(DataItem item) {
        for (Filter f : followers) {
            f.receive(item);
        }
    }

    public void testAllreadyThere(Filter filter) {
        if (followers.contains(filter)) {
            throw new IllegalStateException("Filter is allready in BasisFilter");
        }
    }

}
