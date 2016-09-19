package toni.druck.filter;

import toni.druck.page.DataItem;

/**
 * 
 * @author Thomas Nill
 * 
 * @see Filter implementierung, die nichts tut, ausser ein empfangenes DataItem
 *      weiterzuleiten
 * 
 */
public class EmptyFilter extends BasisFilter {

    public EmptyFilter() {
        super();
    }

    @Override
    public void receive(DataItem item) {
        send(item);
    }

}
